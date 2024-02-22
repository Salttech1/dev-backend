package kraheja.adminexp.billing.dataentry.invoiceCreation.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import kraheja.adminexp.billing.dataentry.invoiceCreation.bean.request.InvoicedetailRequestBean;
import kraheja.adminexp.billing.dataentry.invoiceCreation.bean.request.InvoiceheaderRequestBean;
import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.Invoicedetail;
import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.Invoiceheader;

public final class InvoiceUtility {

    // Private constructor to prevent instantiation
    private InvoiceUtility() {
        throw new AssertionError("InvoiceUtility should not be instantiated.");
    }

    // For comparing attributes of Invoiceheader Entity with InvoiceheaderRequestBean.
    public static List<String> areAttributesEqual(Invoiceheader entity, InvoiceheaderRequestBean requestBean) {
        List<String> differingFields = new ArrayList<>();
        Field[] entityFields = entity.getClass().getDeclaredFields();
        Field[] requestFields = requestBean.getClass().getDeclaredFields();

        for (int i = 0; i < entityFields.length; i++) {
            Field entityField = entityFields[i];
            Field requestField = requestFields[i];

            if (isStringField(entityField) && isStringField(requestField)) {
                String entityValue = getFieldValue(entity, entityField);
                String requestValue = getFieldValue(requestBean, requestField);

                if (!isEqual(entityValue, requestValue)) {
                    differingFields.add(entityField.getName());
                }
            }
        }

        return differingFields;
    }

    // For comparing attributes of Invoiceheader Entity with InvoicedetailRequestBean.
    public static List<String> compareAttributes(Invoicedetail entity, InvoicedetailRequestBean requestBean) {
        List<String> differingFields = new ArrayList<>();
        Field[] entityFields = entity.getClass().getDeclaredFields();
        Field[] requestFields = requestBean.getClass().getDeclaredFields();

        for (int i = 0; i < entityFields.length; i++) {
            Field entityField = entityFields[i];
            Field requestField = requestFields[i];

            if (isStringField(entityField) && isStringField(requestField)) {
                String entityValue = getFieldValue(entity, entityField);
                String requestValue = getFieldValue(requestBean, requestField);

                if (!isEqual(entityValue, requestValue)) {
                    differingFields.add(entityField.getName());
                }
            }
        }

        return differingFields;
    }

    // To check if the request field is a String or No.
    private static boolean isStringField(Field field) {
        return field.getType() == String.class;
    }

    // To fetch the field value.
    private static String getFieldValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            return (String) field.get(object);
        } catch (IllegalAccessException e) {
            // Handle the exception as needed
            return null;
        }
    }

    // To check if the two attributes of the corresponding objects are equal.
    private static boolean isEqual(String entityValue, String requestValue) {
        return StringUtils.trimWhitespace(entityValue).equals(StringUtils.trimWhitespace(requestValue));
    }
}
