package by.pvt.kish.pojos;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Kish Alexey
 */
@Entity
public class Meeting {

    @Id
    @GeneratedValue
    private Long meetingId;

    @Column
    private String subject;

    @ManyToMany(mappedBy = "meetings")
    private Set<Employee> employees = new HashSet<Employee>();

    public Meeting(String subject) {
        this.subject = subject;
    }

    public Meeting() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meeting meeting = (Meeting) o;

        if (meetingId != null ? !meetingId.equals(meeting.meetingId) : meeting.meetingId != null) return false;
        return subject != null ? subject.equals(meeting.subject) : meeting.subject == null;

    }

    @Override
    public int hashCode() {
        int result = meetingId != null ? meetingId.hashCode() : 0;
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        return result;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "meetingId=" + meetingId +
                ", subject='" + subject + '\'' +
                '}';
    }
}
