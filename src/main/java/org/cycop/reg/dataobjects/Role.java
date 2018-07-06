package org.cycop.reg.dataobjects;

public class Role {

    public String roleCode;
    public String roleDescription;

    public Role(){

    }
    public Role(String roleCode, String roleDescription){
        this.roleCode = roleCode;
        this.roleDescription = roleDescription;
    }

    public String getRoleCode(){
        return roleCode;
    }

    public void setRoleCode(String roleCode){
        this.roleCode = roleCode;
    }

    public String getRoleDescription(){
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription){
        this.roleDescription = roleDescription;
    }

    @Override
    public boolean equals(Object r){
        if (r != null && r instanceof Role) {
            Role role = (Role)r;
            return role.getRoleCode().equals(this.roleCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return roleCode.hashCode();
    }
}
