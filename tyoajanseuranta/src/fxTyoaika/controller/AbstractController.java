package fxTyoaika.controller;

public class AbstractController {
    protected final ModelAccess modelAccess;
    
    public AbstractController(ModelAccess modelAccess) {
        this.modelAccess = modelAccess;
    }
    
    public ModelAccess getModelAccess(){
        return modelAccess;
    }

}
