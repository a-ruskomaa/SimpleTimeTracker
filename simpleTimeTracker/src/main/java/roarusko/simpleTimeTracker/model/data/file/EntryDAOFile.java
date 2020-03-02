package roarusko.simpleTimeTracker.model.data.file;

import java.time.format.DateTimeParseException;
import java.util.List;

import roarusko.simpleTimeTracker.model.data.EntryDAO;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.utility.Entries;

/**
 * Merkintöjä tiedostoon tallentava ja lukeva luokka. Tiedostojen käsittely toteutetaan abstraktin yläluokan
 * tarjoamilla metodeilla. Luokka sisältää tarvittavan logiikan, jolla merkkijonoista saadaan luotua Entry-tyyppisiä
 * olioita ja Entry-olioista merkkijonoja.
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class EntryDAOFile extends AbstractDAOFile<Entry> implements EntryDAO {
    private static final String defaultPath = "data/entries.dat";
    
    
    /**
     * Luo uuden EntryDAO:n oletusarvoisella tallennustiedostolla
     */
    public EntryDAOFile() {
        super(defaultPath);
    }
    
    /**
     * Luo uuden EntryDAO:n annetulla tallennustiedostolla
     * @param pathToFile Merkkijonomuotoinen polku haluttuun tallennustiedostoon
     * 
     */
    public EntryDAOFile(String pathToFile) {
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
