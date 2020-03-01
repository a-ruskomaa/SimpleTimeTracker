package roarusko.simpleTimeTracker.model.data.file;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import roarusko.simpleTimeTracker.model.data.DAO;
import roarusko.simpleTimeTracker.model.domain.ChildObject;
import roarusko.simpleTimeTracker.model.domain.DataObject;
import roarusko.simpleTimeTracker.model.domain.ParentObject;
import roarusko.simpleTimeTracker.model.utility.IdGenerator;

public abstract class AbstractDAOFile<T extends DataObject> implements DAO<Integer, T> {
    private final IdGenerator idGen;
    private final Path path;
    
    
    protected AbstractDAOFile(String pathToFile) {
        this.path = createIfNotExists(Paths.get(pathToFile));
        this.idGen = new IdGenerator(getMaxId(path) + 1);
    }
    
    protected abstract T parseObject(String row);
    
    protected abstract String objectToStringForm(T object);
    
    @Override
    public T create(T object) {
        try (FileWriter fw = new FileWriter(path.toFile(), true)) {
            object.setId(idGen.getNewId());
            fw.write(objectToStringForm(object) + "\n");
            fw.close();
            return object;
        } catch (IOException e ){
            e.printStackTrace();
            return object;
        }
    }
    
    
    @Override
    public T read(Integer key) {
        // TODO parempi poikkeuskäsittely
        T object;
        try {
            String row = Files.lines(path)
                    .filter(e -> rowMatchesId(key, e))
                    .findFirst()
                    .get();
            object = parseObject(row);
        } catch (IOException e) {
            System.out.println("File not found! " + e.getMessage());
            return null;
        } catch (NullPointerException e) {
            System.out.println("Object not found! " + e.getMessage());
            return null;
        }
        return object;
    }
    

    @Override
    public boolean update(T object) {
        // TODO parempi poikkeuskäsittely
        boolean updated = false;
        try {
            List<String> rows = Files.readAllLines(path);
            
            for (int i = 0; i < rows.size(); i++) {
                if (rowMatchesId(object.getId(), rows.get(i))) {
                    rows.set(i, objectToStringForm(object));
                    updated = true;
                }
            }
            Files.write(path, rows);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updated;
    }


    @Override
    public boolean delete(Integer key) {
        boolean deleted = false;
        try {
            List<String> rows = Files.readAllLines(path);
            
            for (int i = 0; i < rows.size(); i++) {
                if (rowMatchesId(key, rows.get(i))) {
                    rows.remove(i);
                    deleted = true;
                }
            }

            Files.write(path, rows);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deleted;
    }
    
    
    @Override
    public List<T> list() {
        try {
            List<T> objects = Files.lines(path)
                    .map(row -> parseObject(row))
                    .collect(Collectors.toList());
            return objects;
        } catch (IOException e) {
            System.out.println("File not found! " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("File not found! " + e.getMessage());
        }
        return new ArrayList<T>();
    }
    
    
    
    /**
     * @param object asd
     * @return asd
     */
    protected List<T> list(ParentObject object) {
        List<T> objects = list().stream()
                .filter(e -> ((ChildObject) e).getOwnerId() == object.getId())
                .collect(Collectors.toList());
            return objects;
    }
    
    
    // Apumetodit
    
    private final int getMaxId(Path pathToFile) {
        int max = 0;
        try {
            max = Files.lines(pathToFile)
                    .mapToInt(row -> parseId(row))
                    .max()
                    .orElse(0);
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return max;
    }
    
    
    private final boolean rowMatchesId(Integer key, String row) {
        return parseId(row) == key;
    }
    
    
    private final int parseId(String row) {
        return Integer.parseUnsignedInt(row, 0, row.indexOf(','), 10);
    }

    
    private Path createIfNotExists(Path path) {
        if (Files.exists(path)) return path;
        try {
            Files.createDirectories(path.getParent());
            return Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
