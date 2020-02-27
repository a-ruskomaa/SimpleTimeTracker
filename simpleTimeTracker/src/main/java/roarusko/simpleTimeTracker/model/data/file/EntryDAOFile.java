package roarusko.simpleTimeTracker.model.data.file;

import java.time.format.DateTimeParseException;
import java.util.List;

import roarusko.simpleTimeTracker.model.data.EntryDAO;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.utility.Entries;

public class EntryDAOFile extends AbstractDAOFile<Entry> implements EntryDAO {
    private static final String pathToFile = "data/entries.dat";
    
    
    public EntryDAOFile() {
        super(pathToFile);
    }

    @Override
    public List<Entry> list(Project project) {
        return super.list(project);
    }


    @Override
    protected Entry parseObject(String row) {
        String[] parts = row.split(",");
        try {
            return new Entry(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Entries.parseDateTimeFromString(parts[2]),
                            Entries.parseDateTimeFromString(parts[3]));
        } catch (NullPointerException e) {
            System.out.println("Not enough fields" + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Wrong input type" + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Wrong date format" + e.getMessage());
        }
        return null;
    }


    @Override
    protected String objectToStringForm(Entry entry) {
        return String.format("%d,%d,%s,%s",
                entry.getId(),
                entry.getOwnerId(),
                Entries.getDateTimeAsString(entry.getStartDateTime()),
                Entries.getDateTimeAsString(entry.getEndDateTime()));
    }

}
