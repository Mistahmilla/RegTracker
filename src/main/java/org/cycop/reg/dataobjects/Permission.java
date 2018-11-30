package org.cycop.reg.dataobjects;

public class Permission extends DataObject {

    private String permissionCode;
    private String permissionName;
    private String permissionDescription;

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }

    @Override
    public boolean equals(Object p){
        if (p instanceof Permission) {
            Permission perm = (Permission) p;
            return perm.getPermissionCode().equals(this.permissionCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return permissionCode.hashCode();
    }
}
