package kraheja.commons.utils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StringType;
import org.hibernate.type.descriptor.sql.CharTypeDescriptor;
import org.hibernate.usertype.UserType;

public class CharType implements UserType {
	@Override
	public int[] sqlTypes() {
		return new int[] { CharTypeDescriptor.INSTANCE.getSqlType() };
	}

	@Override
	public Class returnedClass() {
		return String.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		int a,b;
		if(ObjectUtils.isEmpty(x))
			x=0;
		if(ObjectUtils.isEmpty(y))
			y=0;
		 a=x.hashCode();
		 b=y.hashCode();
//		return x == y;
		 return a == b;
	}

	@Override
	public int hashCode(Object o) throws HibernateException {
		return o == null ? 0 : o.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] strings,
			SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
			throws HibernateException, SQLException {
		final String value = (String) StringType.INSTANCE.get(resultSet, strings[0], sharedSessionContractImplementor);
		return null == value ? null : value.trim();
	}

	@Override
	public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i,
			SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
		String result = null == o ? null : o.toString();
		if (null != result) {
			result = String.format("%1$-" + preparedStatement.getParameterMetaData().getPrecision(i) + "s", result);
		}
		StringType.INSTANCE.set(preparedStatement, result, i, sharedSessionContractImplementor);
	}

	@Override
	public Object deepCopy(Object o) throws HibernateException {
		return (null == o) ? null : (String) o;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object o) throws HibernateException {
		return (Serializable) o;
	}

	@Override
	public Object assemble(Serializable serializable, Object o) throws HibernateException {
		return serializable;
	}

	@Override
	public Object replace(Object o, Object o1, Object o2) throws HibernateException {
		return o;
	}
}
