package entities.Configuration;

public class Users {

    private Admin admin;

    private NonAdmin nonAdmin;

    public Admin getAdmin ()
    {
        return admin;
    }

    public void setAdmin (Admin admin)
    {
        this.admin = admin;
    }

    public NonAdmin getNonAdmin ()
    {
        return nonAdmin;
    }

    public void setNonAdmin (NonAdmin nonAdmin)
    {
        this.nonAdmin = nonAdmin;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Admin = "+admin+", NonAdmin = "+nonAdmin+"]";
    }
}
