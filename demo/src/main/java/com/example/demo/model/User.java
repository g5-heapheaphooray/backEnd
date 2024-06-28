package com.example.demo.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type",
        discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("U")
public class User implements UserDetails {

    @Id 
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "password")
    private String password;

    @Column(name = "complain_count")
    // both volunteer and organisation will have but the limit diff ig
    private int complainCount;


    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "pfp")
    private Media pfp;


    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.DETACH})
//    https://stackoverflow.com/questions/43235303/how-to-delete-a-row-in-join-table-with-jpa
//    https://www.baeldung.com/jpa-cascade-types
    @JoinTable(
            name = "events_part",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "event_id") })
    private Set<Event> eventsPart;
    // only applies to Organisation subclass
    // if Volunteer subclass, this field should be null

    // @ElementCollection(targetClass = Event.class, fetch = FetchType.EAGER)
    // @CollectionTable(name = "events_org", joinColumns = @JoinColumn(name = "event_id"))
    // @Column(name = "event_org", nullable = true)
    // private List<Event> eventsOrg;
    // only applies to Volunteer subclass
    // if Organisation subclass, this field should be null

//    @ManyToMany
//    @JoinTable(
//            name = "events_org",
//            joinColumns = @JoinColumn(name = "user_email"),
//            inverseJoinColumns = @JoinColumn(name = "event_id"))
//    private List<Event> eventsOrg;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Event> eventsOrg;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "locked")
    private boolean locked;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    public User(){
    }

    public User(String fullName, String email, String contactNo, String password, Set<Event> eventsPart, List<Event> eventsOrg, Role role){
        this.fullName = fullName;
        this.email = email;
        this.contactNo = contactNo;
        this.password = password;
        this.eventsPart = eventsPart;
        this.eventsOrg = eventsOrg;
        this.role = role;
        this.pfp = new Media("default.png", "./media/pfp/default.png");
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getComplainCount() {
        return complainCount;
    }

    public void setComplainCount(int complainCount) {
        this.complainCount = complainCount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public Set<Event> getEventsPart() {
        return eventsPart;
    }

    public void setEventsPart(Set<Event> eventsPart) {
        this.eventsPart = eventsPart;
    }

    public List<Event> getEventsOrg() {
        return eventsOrg;
    }

    public void setEventsOrg(List<Event> eventsOrg) {
        this.eventsOrg = eventsOrg;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toString());

        return List.of(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !(this.locked);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Role getRole() {
        return role;
    }

    public User setRole(Role role) {
        this.role = role;

        return this;
    }

    public boolean addEventOrg(Event e) {
        System.out.println(getEventsOrg());
//        if (!isAccountNonLocked() || getEventsOrg() == null) {
//            return false;
//        }
        List<Event> currentE = getEventsOrg();
        currentE.add(e);
        setEventsOrg(currentE);
        System.out.println(currentE);
        System.out.println(getEventsOrg());
        return true;
    }

    public Media getPfp() {
        return pfp;
    }

    public void setPfp(Media pfp) {
        this.pfp = pfp;
    }

    
}
