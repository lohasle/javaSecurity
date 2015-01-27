package theDemo.security.hjsec;

/*import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarProxy;
import org.hyperic.sigar.SigarProxyCache;*/

public class MacTest {
   /* private Sigar      sigar;
    private SigarProxy proxy;
    
    public MacTest() {
        this.sigar = new Sigar();
        this.proxy = SigarProxyCache.newInstance(this.sigar);
    }
    
    public String[] getAllMacAddress() {
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
    
    public String getMacAddress() {
        try {
            String[] names = this.proxy.getNetInterfaceList();
            String mac = null;
            if (names != null && names.length > 0) {
                for (int i = 0; i < names.length; i++) {
                    NetInterfaceConfig nic = this.sigar.getNetInterfaceConfig(names[i]);
                    String ip = nic.getAddress();
                    if (ip != null && !"127.0.0.1".equals(ip) && !"0.0.0.0".equals(ip)) {
                        System.out.println(ip);
                        mac = nic.getHwaddr();
                    }
                }
            }
            return mac;
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private String getMacAddress(String name) {
        try {
            NetInterfaceConfig nic = this.sigar.getNetInterfaceConfig(name);
            System.out.println("ip:" + nic.getAddress() + "  mac:" + nic.getHwaddr());
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
        System.out.println("-------------mac---------------");
        MacTest mt = new MacTest();
        System.out.println(mt.getMacAddress());
    }*/
}
