package prg.training.mantis.model;

import javax.persistence.*;

/**
 * Created by QA Lady on 5/21/2017.
 */

@Entity
@Table(name = "mantis_bug_table")
public class IssuesData {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "summary")
    private String summary;

    @Column(name = "status")
    private short status;

    @Transient
    private String description;
    @Transient
    private Project project;


    public int getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public Project getProject() {
        return project;
    }

    public int getStatus() {
        return status;
    }

    public IssuesData withId(int id) {
        this.id = id;
        return this;
    }

    public IssuesData withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public IssuesData withDescription(String description) {
        this.description = description;
        return this;
    }

    public IssuesData withProject(Project project) {
        this.project = project;
        return this;
    }

    public IssuesData withStatus(short status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "IssuesData{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssuesData that = (IssuesData) o;

        if (id != that.id) return false;
        if (summary != null ? !summary.equals(that.summary) : that.summary != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return project != null ? project.equals(that.project) : that.project == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        return result;
    }

}
