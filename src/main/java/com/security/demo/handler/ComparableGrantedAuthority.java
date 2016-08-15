package com.security.demo.handler;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

/**
 * 重写SimpleGrantedAuthority <br>
 * for memcache 反序列化的要求
 * 
 */
public class ComparableGrantedAuthority implements GrantedAuthority, Comparable<ComparableGrantedAuthority> {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final String role;

    public ComparableGrantedAuthority(String role) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    public String getAuthority() {
        return role;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof ComparableGrantedAuthority) {
            return role.equals(((ComparableGrantedAuthority) obj).getAuthority());
        }

        return false;
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }

	@Override
	public int compareTo(ComparableGrantedAuthority o) {
		return getAuthority().compareTo(o.getAuthority());
	}
}
