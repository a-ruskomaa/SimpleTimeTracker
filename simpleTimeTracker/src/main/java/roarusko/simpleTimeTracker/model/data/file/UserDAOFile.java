package roarusko.simpleTimeTracker.model.data.file;

import roarusko.simpleTimeTracker.model.data.UserDAO;
import roarusko.simpleTimeTracker.model.domain.User;

public class UserDAOFile extends AbstractDAOFile<User> implements UserDAO {
    private static final String pathToFile = "data/users.dat";
     
     public UserDAOFile() {
        super(pathToFile);
    }

    
    @Override
    protected User parseObject(String row) {
        String[] parts = row.split(",");
        return new User(Integer.parseInt(parts[0]), parts[1]);
    }
    
    
    @Override
    protected String objectToStringForm(User user) {
        return String.format("%d,%s", user.getId(), user.getName());
    }

}
