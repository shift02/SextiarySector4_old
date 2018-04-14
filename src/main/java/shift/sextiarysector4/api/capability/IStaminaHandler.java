package shift.sextiarysector4.api.capability;

public interface IStaminaHandler {
    
    /**
     * @return スタミナの回復量
     */
    int getStamina();
    
    /**
     * @return 隠しスタミナの回復量
     */
    float getStaminaSaturation();
    
    /**
     * @return スタミナを減らす量
     */
    float getStaminaExhaustion();
    
    void setStamina(int m);
    
    void setStaminaSaturation(float m);
    
    void setStaminaExhaustion(float m);
    
}
