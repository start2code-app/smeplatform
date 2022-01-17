create trigger GCSPLATFORM_LOCATION_DELETE_TS_NN_TRIGGER before update on GCSPLATFORM_LOCATION
for each row
    if not(NEW.DELETE_TS <=> OLD.DELETE_TS) then
        set NEW.DELETE_TS_NN = if (NEW.DELETE_TS is null, '1000-01-01 00:00:00.000', NEW.DELETE_TS);
    end if ;