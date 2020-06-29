package co.edu.unal.software_engineering.labs.service;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.SearchControls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import co.edu.unal.software_engineering.labs.pojo.LoginUserPOJO;
import co.edu.unal.software_engineering.labs.pojo.RegisterUserPOJO;
import co.edu.unal.software_engineering.labs.repository.UserLdapRepository;

@Service
public class UserLdapService implements UserLdapRepository {

	public static final String LDAP_FIELD_PWR = "userPassword";
	public static final String LDAP_FIELD_UID = "uid";
	public static final String LDAP_FIELD_CN = "cn";
	public static final String LDAP_FIELD_SN = "sn";
	public static final String LDAP_SCHEME_ORG = "inetOrgPerson";
	public static final String LDAP_SCHEME_TOP = "top";

	@Autowired
	private LdapTemplate ldapTemplate;

	@Override
	public String create(RegisterUserPOJO user) {
		Name dn = buildDn(user.getUsername());
		ldapTemplate.bind(dn, null, buildAttributes(user));
		return user.getUsername();
	}

	@Override
	public String update(RegisterUserPOJO user) {
		Name dn = buildDn(user.getUsername());
		ldapTemplate.rebind(dn, null, buildAttributes(user));
		return user.getUsername();
	}

	@Override
	public String remove(String userId) {
		Name dn = buildDn(userId);
		// ldapTemplate.unbind(dn, true); //Remove recursively all entries
		ldapTemplate.unbind(dn);
		return userId;
	}

	@Override
	public List<RegisterUserPOJO> list() {
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		List<RegisterUserPOJO> people = ldapTemplate.search(query().where("objectclass").is(LDAP_SCHEME_ORG),
				new PersonAttributeMapper());
		return people;
	}

	@Override
	public Boolean exist(LoginUserPOJO user) {
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		List<RegisterUserPOJO> people = ldapTemplate.search(query().where("objectclass").is(LDAP_SCHEME_ORG)
				.and(LDAP_FIELD_UID).is(user.getUsername()).and(LDAP_FIELD_PWR).is(digestSHA(user.getPassword())),
				new PersonAttributeMapper());

		if (people.isEmpty() || people.size() > 1)
			return false;
		return true;
	}

	private Attributes buildAttributes(RegisterUserPOJO p) {

		BasicAttribute ocattr = new BasicAttribute("objectclass");
		ocattr.add(LDAP_SCHEME_TOP);
		ocattr.add(LDAP_SCHEME_ORG);

		Attributes attrs = new BasicAttributes();
		attrs.put(ocattr);
		attrs.put(LDAP_FIELD_UID, p.getUsername());
		attrs.put(LDAP_FIELD_CN, p.getNames());
		attrs.put(LDAP_FIELD_SN, p.getSurnames());
		attrs.put(LDAP_FIELD_PWR, digestSHA(p.getPassword()));
		return attrs;
	}

	public Name buildDn(String userId) {
		return LdapNameBuilder.newInstance().add(LDAP_FIELD_UID, userId).build();
	}

	public Name buildBaseDn() {
		return LdapNameBuilder.newInstance().build();
	}

	private class PersonAttributeMapper implements AttributesMapper<RegisterUserPOJO> {

		@Override
		public RegisterUserPOJO mapFromAttributes(Attributes attributes) throws NamingException {
			RegisterUserPOJO user = new RegisterUserPOJO();
			user.setUsername(
					null != attributes.get(LDAP_FIELD_UID) ? attributes.get(LDAP_FIELD_UID).get().toString() : null);
			user.setNames(
					null != attributes.get(LDAP_FIELD_CN) ? attributes.get(LDAP_FIELD_CN).get().toString() : null);
			user.setSurnames(
					null != attributes.get(LDAP_FIELD_SN) ? attributes.get(LDAP_FIELD_SN).get().toString() : null);
			user.setId_documment(0);
			user.setPassword(
					null != attributes.get(LDAP_FIELD_PWR) ? attributes.get(LDAP_FIELD_PWR).get().toString() : null);
			return user;
		}
	}

	private String digestSHA(final String password) {
		String base64;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA");
			digest.update(password.getBytes());
			base64 = Base64.getEncoder().encodeToString(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		return "{SHA}" + base64;
	}
}