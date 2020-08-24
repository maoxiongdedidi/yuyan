package com.yueyi.yuyinfanyi.bean;

public class APPBean {

    /**
     * versionInfoVo : {"content":"string","forceUp":true,"linkUrl":"string","title":"更新提示","version":"2.1.0"}
     */

    private VersionInfoVoBean versionInfoVo;

    public VersionInfoVoBean getVersionInfoVo() {
        return versionInfoVo;
    }

    public void setVersionInfoVo(VersionInfoVoBean versionInfoVo) {
        this.versionInfoVo = versionInfoVo;
    }

    public static class VersionInfoVoBean {
        /**
         * content : string
         * forceUp : true
         * linkUrl : string
         * title : 更新提示
         * version : 2.1.0
         */

        private String content;
        private boolean forceUp;
        private String linkUrl;
        private String title;
        private String version;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isForceUp() {
            return forceUp;
        }

        public void setForceUp(boolean forceUp) {
            this.forceUp = forceUp;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
