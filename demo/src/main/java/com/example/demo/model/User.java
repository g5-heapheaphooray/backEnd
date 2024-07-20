package com.example.demo.model;

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
    private int complainCount;


    @Column(name = "contact_no")
    private String contactNo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "media_id", referencedColumnName = "id")
    private PfpMedia pfp;

    @ManyToMany(fetch = FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.DETACH})
//    https://stackoverflow.com/questions/43235303/how-to-delete-a-row-in-join-table-with-jpa
//    https://www.baeldung.com/jpa-cascade-types
    @JoinTable(
            name = "events_part",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "event_id") })
    private Set<Event> eventsPart;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "organisation")
    private Set<Event> eventsOrg;

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

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "verificiation_token_creation")
    private Date verificationTokenCreatedAt;

    public User(){
    }

    public User(String fullName, String email, String contactNo, String password, Set<Event> eventsPart, Set<Event> eventsOrg, Role role, String pfpFp){
        this.fullName = fullName;
        this.email = email;
        this.contactNo = contactNo;
        this.password = password;
        this.eventsPart = eventsPart;
        this.eventsOrg = eventsOrg;
        this.role = role;
        this.pfp =  new PfpMedia("default.png", pfpFp, this);
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

    public Set<Event> getEventsOrg() {
        return eventsOrg;
    }

    public void setEventsOrg(Set<Event> eventsOrg) {
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
        return !this.locked;
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
        Set<Event> currentE = getEventsOrg();
        currentE.add(e);
        setEventsOrg(currentE);
        return true;
    }

    public PfpMedia getPfp() {
        return pfp;
    }

    public void setPfp(PfpMedia pfp) {
        this.pfp = pfp;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
        setVerificationTokenCreatedAt(new Date());
    }

    public Date getVerificationTokenCreatedAt() {
        return verificationTokenCreatedAt;
    }

    public void setVerificationTokenCreatedAt(Date verificationTokenCreatedAt) {
        this.verificationTokenCreatedAt = verificationTokenCreatedAt;
    }

    public boolean tokenStillValid() {
        Date now = new Date();
        if ((now.getTime() - this.verificationTokenCreatedAt.getTime()) >= 15*60*1000) {
            setVerificationToken(null);
            return false;
        }
        return true;
    }
}
