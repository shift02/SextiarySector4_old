package shift.sextiarysector4.api.capability;

public interface IMoistureHandler {
    
    /**
     * @return 水分の回復量
     */
    int getMoisture();
    
    /**
     * @return 隠し水分の回復量
     */
    float getMoistureSaturation();
    
    /**
     * @return 水分を減らす量
     */
    float getMoistureExhaustion();
    
    void setMoisture(int m);
    
    void setMoistureSaturation(float m);
    
    void setMoistureExhaustion(float m);
    
}
