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
        this.path = Paths.get(pathToFile);
        this.idGen = new IdGenerator(getMaxId());
    }
    
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
        T object;
        try {
            String row = Files.lines(path)
                    .filter(e -> rowMatchesId(key, e))
                    .findFirst()
                    .get();
            object = parseObject(row);
        } catch (IOException e) {
            System.out.println("File not found!");
            return null;
        } catch (NullPointerException e) {
            System.out.println("Object not found");
            return null;
        }
        return object;
    }
    

    @Override
    public boolean update(T object) {
        try {
            List<String> rows = Files.readAllLines(path);
            
            for (int i = 0; i < rows.size(); i++) {
                if (rowMatchesId(object.getId(), rows.get(i))) {
                    rows.set(i, objectToStringForm(object));
                    break;
                }
            }

            Files.write(path, rows);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean delete(Integer key) {
        try {
            List<String> rows = Files.readAllLines(path);
            
            for (int i = 0; i < rows.size(); i++) {
                if (rowMatchesId(key, rows.get(i))) {
                    rows.remove(i);
                }
            }

            Files.write(path, rows);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    @Override
    public List<T> list() {
        try {
            List<T> objects = Files.lines(createIfNotExists(path))
                    .map(row -> parseObject(row))
                    .collect(Collectors.toList());
            return objects;
        } catch (IOException e) {
            System.out.println("File not found!");
            return new ArrayList<T>();
        } catch (NullPointerException e) {
            System.out.println("File not found");
            return new ArrayList<T>();
        }
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
    
    
    private final int getMaxId() {
        try {
            int max = Files.lines(path)
                    .mapToInt(row -> parseId(row))
                    .max()
                    .orElse(0);
            return max + 1;
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
            return 0;
        }
    }
    
    
    private final boolean rowMatchesId(Integer key, String row) {
        return parseId(row) == key;
    }
    
    private final int parseId(String row) {
        return Integer.parseUnsignedInt(row, 0, row.indexOf(','), 10);
    }
    
    protected abstract T parseObject(String row);
    
    protected abstract String objectToStringForm(T object);
    
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
