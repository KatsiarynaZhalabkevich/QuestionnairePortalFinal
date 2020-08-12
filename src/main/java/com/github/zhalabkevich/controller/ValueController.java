package com.github.zhalabkevich.controller;

import com.github.zhalabkevich.controller.util.ContextMessage;
import com.github.zhalabkevich.domain.Field;
import com.github.zhalabkevich.domain.FieldValue;
import com.github.zhalabkevich.domain.Users;
import com.github.zhalabkevich.service.ServiceException;
import com.github.zhalabkevich.service.ValueService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class ValueController implements Serializable {
    private final static Logger logger = LogManager.getLogger(ValueController.class);
    private List<String> values = new ArrayList<>();
    private Field field;
    private String singleVal;
    private Map<Field, String> valueMap = new HashMap<>();

    private final static String ERROR = "Error";
    private final static String SUCCESS = "Success";


    @EJB
    private ValueService valueService;

    public String newResponse() {
        return "/index?faces-redirect=true";
    }

    public String saveResponse() {

        FacesContext context = FacesContext.getCurrentInstance();
        Users user = (Users) context.getExternalContext().getSessionMap().get("user");
        List<FieldValue> fieldValueList = new ArrayList<>();
        String UUID = java.util.UUID.randomUUID().toString();


        for (Field f : valueMap.keySet()) {

            FieldValue fv = new FieldValue();
            fv.setResponseId(UUID);
            fv.setField(f);
            fv.setUser(user);
            fv.setValue(valueMap.get(f));
            fieldValueList.add(fv);
        }
        try {
            valueService.saveResponse(fieldValueList);
            ContextMessage.contextMessage(SUCCESS, "Response was saved!");

        } catch (ServiceException e) {
            logger.info(e.getMessage());
            ContextMessage.contextMessage(ERROR, "Can't save data to server. Try again letter.");
        } finally {
            valueMap = new HashMap<>();
        }
        return "/thanks_page?faces-redirect=true";
    }

    public String cancel() {
        return "/main?faces-redirect=true";
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getSingleVal() {
        return singleVal;
    }

    public void setSingleVal(String singleVal) {
        this.singleVal = singleVal;
    }

    public Map<Field, String> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<Field, String> valueMap) {
        this.valueMap = valueMap;
    }

    public void setValueMap(Field field, String value) {
        this.valueMap.put(field, value);
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
