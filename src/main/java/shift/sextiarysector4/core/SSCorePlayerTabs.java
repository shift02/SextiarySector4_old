package shift.sextiarysector4.core;

import shift.sextiarysector4.core.gui.tab.AbstractTab;
import shift.sextiarysector4.core.gui.tab.InventoryTabSS;
import shift.sextiarysector4.core.gui.tab.TabManager;

public class SSCorePlayerTabs {
    
    public static AbstractTab ss;
    
    public static void initTabs() {
        
        ss = new InventoryTabSS();
        TabManager.registerTab(ss);
        
    }
    
}
