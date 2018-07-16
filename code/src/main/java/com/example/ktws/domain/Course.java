package com.example.ktws.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private String address;

    private String camera;

    @Column(name = "num_of_student")
    private Integer numOfStudent;

    @Column(name = "intervals")
    private Integer interval;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "course_time_slot", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "time_slot_id"))
    private Set<TimeSlot> timeSlots = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Section> sections = new HashSet<>();

    public void addSection(Section section) {
        if (sections.contains(section)) {
            return;
        }
        sections.add(section);
        section.setCourse(this);
    }

    public void removeSection(Section section) {
        if (!sections.contains(section)) {
            return;
        }
        sections.remove(section);
        section.setCourse(null);
    }

    public void addTimeSlot(TimeSlot timeSlot) {
        if (timeSlots.contains(timeSlot)) {
            return;
        }
        timeSlots.add(timeSlot);
        timeSlot.addCourse(this);
    }

    public void removeTimeSlot(TimeSlot timeSlot) {
        if (!timeSlots.contains(timeSlot)) {
            return;
        }
        timeSlots.remove(timeSlot);
        timeSlot.removeCourse(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public Integer getNumOfStudent() {
        return numOfStudent;
    }

    public void setNumOfStudent(Integer numOfStudent) { this.numOfStudent = numOfStudent; }

    public Integer getInterval() { return interval; }

    public void setInterval(Integer intervals) { this.interval = intervals; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(Set<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }
}