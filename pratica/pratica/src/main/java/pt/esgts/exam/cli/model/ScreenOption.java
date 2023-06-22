package pt.esgts.exam.cli.model;

/**
 * A screen option to be shown in a menu
 *
 * @author Bruno Jesus
 * @version 1.0
 */
public class ScreenOption {
    private int id;
    private String name;
    private Runnable action;

    public ScreenOption() {
    }

    /**
     * The option's id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the option's id
     * @param id the new desired id
     * @return the {@link ScreenOption}
     */
    public ScreenOption setId(int id) {
        this.id = id;
        return this;
    }

    /**
     * The option's name (label)
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the option's name
     * @param name the new desired name
     * @return the {@link }
     */
    public ScreenOption setName(String name) {
        this.name = name;
        return this;
    }

    public Runnable getAction() {
        return action;
    }

    public ScreenOption setAction(Runnable action) {
        this.action = action;
        return this;
    }

    public String toTextMenuOption() {
        return String.format("%d: %s", id, name);
    }
}
