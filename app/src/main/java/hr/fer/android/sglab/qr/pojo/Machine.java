package hr.fer.android.sglab.qr.pojo;

public class Machine {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String ACTIVE_POWER = "activePower";
    public static final String REACTIVE_POWER = "reactivePower";
    public static final String APPARENT_POWER = "apparentPower";

    private Integer id;
    private String name;
    private String description;
    private Double activePower;
    private Double reactivePower;
    private Double apparentPower;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getActivePower() {
        return activePower;
    }

    public void setActivePower(Double activePower) {
        this.activePower = activePower;
    }

    public Double getReactivePower() {
        return reactivePower;
    }

    public void setReactivePower(Double reactivePower) {
        this.reactivePower = reactivePower;
    }

    public Double getApparentPower() {
        return apparentPower;
    }

    public void setApparentPower(Double apparentPower) {
        this.apparentPower = apparentPower;
    }


    @Override
    public String toString() {
        return "Machine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", activePower=" + activePower +
                ", reactivePower=" + reactivePower +
                ", apparentPower=" + apparentPower +
                '}';
    }
}
