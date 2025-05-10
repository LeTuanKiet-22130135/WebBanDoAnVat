package newmodel;

public class User {
    private int id;
    private String username;
    private String hashedPassword;
    private boolean isAdmin;

    // Constructors
    public User() {
    }

    public User(int id, String username, String hashedPassword, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.isAdmin = isAdmin;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}