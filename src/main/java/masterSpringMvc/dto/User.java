package masterSpringMvc.dto;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * user dao实现
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String oauthId;

    @Column(nullable = true)
    private String oauthAddress;

    @Column(nullable = false)
    private int  type;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    public String getOauthAddress() {
        return oauthAddress;
    }

    public void setOauthAddress(String oauthAddress) {
        this.oauthAddress = oauthAddress;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
