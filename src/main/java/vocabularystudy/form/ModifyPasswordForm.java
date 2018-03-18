package vocabularystudy.form;

public class ModifyPasswordForm
{
    private String username;

    private String originPassword;

    private String newPassword;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getOriginPassword()
    {
        return originPassword;
    }

    public void setOriginPassword(String originPassword)
    {
        this.originPassword = originPassword;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }
}
