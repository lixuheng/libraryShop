package cn.bewweb.entities;

import java.util.Date;

public class CSystem {
    private Long systemId;

    private Date startupDatetime;

    private Integer todayVisite;

    private Long allVised;

    private Date shutdownDatetime;

    private String logPath;

    private String serverIp;

    private String domainName;

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public Date getStartupDatetime() {
        return startupDatetime;
    }

    public void setStartupDatetime(Date startupDatetime) {
        this.startupDatetime = startupDatetime;
    }

    public Integer getTodayVisite() {
        return todayVisite;
    }

    public void setTodayVisite(Integer todayVisite) {
        this.todayVisite = todayVisite;
    }

    public Long getAllVised() {
        return allVised;
    }

    public void setAllVised(Long allVised) {
        this.allVised = allVised;
    }

    public Date getShutdownDatetime() {
        return shutdownDatetime;
    }

    public void setShutdownDatetime(Date shutdownDatetime) {
        this.shutdownDatetime = shutdownDatetime;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath == null ? null : logPath.trim();
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp == null ? null : serverIp.trim();
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName == null ? null : domainName.trim();
    }
}