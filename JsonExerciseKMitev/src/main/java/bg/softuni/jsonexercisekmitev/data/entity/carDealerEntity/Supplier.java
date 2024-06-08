package bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity;

import bg.softuni.jsonexercisekmitev.data.entity.BaseEntity;
import com.google.gson.annotations.Expose;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity {

    @Expose
    private String name;
    @Expose
    private boolean isImporter;
    @OneToMany(mappedBy = "supplier",fetch = FetchType.EAGER)
    private Set<Part> parts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }

    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }
}
