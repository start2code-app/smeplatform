package com.gcs.gcsplatform.global;

import com.haulmont.chile.core.model.Instance;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.entity.BaseGenericIdEntity;
import com.haulmont.cuba.core.global.MetadataTools;
import org.apache.commons.lang3.exception.ExceptionUtils;

import static com.haulmont.bali.util.Preconditions.checkNotNullArgument;

public class ExtMetadataTools extends MetadataTools {

    /**
     * Works the same as original copy method except that system properties are not being copied to destination entity.
     *
     * @param source source instance
     * @param dest   destination instance
     */
    @Override
    public void copy(Instance source, Instance dest) {
        checkNotNullArgument(source, "source is null");
        checkNotNullArgument(dest, "dest is null");

        MetaClass sourceMetaClass = metadata.getClassNN(source.getClass());
        MetaClass destMetaClass = metadata.getClassNN(dest.getClass());
        for (MetaProperty srcProperty : sourceMetaClass.getProperties()) {
            String name = srcProperty.getName();
            MetaProperty dstProperty = destMetaClass.getProperty(name);
            if (dstProperty != null && !dstProperty.isReadOnly() && !isSystem(dstProperty)
                    && persistentAttributesLoadChecker.isLoaded(source, name)) {
                try {
                    dest.setValue(name, source.getValue(name));
                } catch (RuntimeException e) {
                    Throwable cause = ExceptionUtils.getRootCause(e);
                    if (cause == null)
                        cause = e;
                    // ignore exception on copy for not loaded fields
                    if (!isNotLoadedAttributeException(cause)) {
                        throw e;
                    }
                }
            }
        }

        if (source instanceof BaseGenericIdEntity && dest instanceof BaseGenericIdEntity) {
            ((BaseGenericIdEntity) dest).setDynamicAttributes(((BaseGenericIdEntity<?>) source).getDynamicAttributes());
        }
    }

    private boolean isNotLoadedAttributeException(Throwable e) {
        return e instanceof IllegalStateException
                || e instanceof org.eclipse.persistence.exceptions.ValidationException && e.getMessage() != null
                && e.getMessage().contains("An attempt was made to traverse a relationship using indirection that had a null Session");
    }
}
