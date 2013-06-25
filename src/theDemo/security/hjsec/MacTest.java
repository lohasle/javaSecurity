package theDemo.security.hjsec;

import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarProxy;
import org.hyperic.sigar.SigarProxyCache;

public class MacTest {
    private Sigar      sigar;
    private SigarProxy proxy;
    
    public MacTest() {
        this.sigar = new Sigar();
        this.proxy = SigarProxyCache.newInstance(this.sigar);
    }
    
    public String[] getMacAddress() {
        try {
            String[] names = this.proxy.getNetInterfaceList();
            String mac;
            if (names != null && names.length > 0) {
                String[] macs = new String[names.length];
                for (int i = 0; i < names.length; i++) {
                    mac = this.getMacAddress(names[i]);
                    if (mac != null)
                        macs[i] = mac;
                }
                return macs;
            }
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String getMacAddress(String name) {
        try {
            NetInterfaceConfig nic = this.sigar.getNetInterfaceConfig(name);
            return nic.getHwaddr();
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String getDefaultMacAddress() {
        try {
            NetInterfaceConfig nic = this.sigar.getNetInterfaceConfig();
            return nic.getHwaddr();
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] arg) {
        String[] macs = new MacTest().getMacAddress();
        if (macs != null)
            for (int i = 0; i < macs.length; i++) {
                System.out.println(macs[i]);
            }
        System.out.println(new MacTest().getDefaultMacAddress());
    }
}
