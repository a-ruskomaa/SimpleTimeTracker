package roarusko.simpleTimeTracker.model.data.file;

import java.util.List;

import roarusko.simpleTimeTracker.model.data.EntryDAO;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.utility.Entries;

public class EntryDAOFile extends AbstractDAOFile<Entry> implements EntryDAO {
    
    
    public EntryDAOFile() {
        super("entries.dat");
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
                            Entries.parseDateTimeFromStringNoSeconds(parts[2]),
                            Entries.parseDateTimeFromStringNoSeconds(parts[3]));
        } catch (NullPointerException e) {
            System.out.println("Not enough fields" + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Wrong input type" + e.getMessage());
        }
        return null;
    }


    @Override
    protected String objectToStringForm(Entry entry) {
        return String.format("%d,%d,%s,%s",
                entry.getId(),
                entry.getOwnerId(),
                Entries.getDateTimeAsStringNoSeconds(entry.getStartDateTime()),
                Entries.getDateTimeAsStringNoSeconds(entry.getEndDateTime()));
    }

}
