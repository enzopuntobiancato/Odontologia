/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tesis.mail;

/**
 *
 * @author Enzo
 */
public abstract class Mail {

    private String mail;
    private StringBuilder builder;
    private String title;
    private String content;

    public Mail() {
        this.builder = new StringBuilder();
    }

    /**
     * @return the mail
     */
    public String getMail() {
        mail = getBuilder().toString();
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    public void buildMail() {
        buildHeaderContent();
        setVariableContent();
        addVariableContent();
        buildFooterContent();
    }

    private void buildHeaderContent() {
        getBuilder().append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title></title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><meta name=\"viewport\" content=\"width=320, target-densitydpi=device-dpi\">\n"
                + "<style type=\"text/css\">\n"
                + "/* Mobile-specific Styles */\n"
                + "@media only screen and (max-width: 660px) { \n"
                + "table[class=w0], td[class=w0] { width: 0 !important; }\n"
                + "table[class=w10], td[class=w10], img[class=w10] { width:10px !important; }\n"
                + "table[class=w15], td[class=w15], img[class=w15] { width:5px !important; }\n"
                + "table[class=w30], td[class=w30], img[class=w30] { width:10px !important; }\n"
                + "table[class=w60], td[class=w60], img[class=w60] { width:10px !important; }\n"
                + "table[class=w125], td[class=w125], img[class=w125] { width:80px !important; }\n"
                + "table[class=w130], td[class=w130], img[class=w130] { width:55px !important; }\n"
                + "table[class=w140], td[class=w140], img[class=w140] { width:90px !important; }\n"
                + "table[class=w160], td[class=w160], img[class=w160] { width:180px !important; }\n"
                + "table[class=w170], td[class=w170], img[class=w170] { width:100px !important; }\n"
                + "table[class=w180], td[class=w180], img[class=w180] { width:80px !important; }\n"
                + "table[class=w195], td[class=w195], img[class=w195] { width:80px !important; }\n"
                + "table[class=w220], td[class=w220], img[class=w220] { width:80px !important; }\n"
                + "table[class=w240], td[class=w240], img[class=w240] { width:180px !important; }\n"
                + "table[class=w255], td[class=w255], img[class=w255] { width:185px !important; }\n"
                + "table[class=w275], td[class=w275], img[class=w275] { width:135px !important; }\n"
                + "table[class=w280], td[class=w280], img[class=w280] { width:135px !important; }\n"
                + "table[class=w300], td[class=w300], img[class=w300] { width:140px !important; }\n"
                + "table[class=w325], td[class=w325], img[class=w325] { width:95px !important; }\n"
                + "table[class=w360], td[class=w360], img[class=w360] { width:140px !important; }\n"
                + "table[class=w410], td[class=w410], img[class=w410] { width:180px !important; }\n"
                + "table[class=w470], td[class=w470], img[class=w470] { width:200px !important; }\n"
                + "table[class=w580], td[class=w580], img[class=w580] { width:280px !important; }\n"
                + "table[class=w640], td[class=w640], img[class=w640] { width:300px !important; }\n"
                + "table[class*=hide], td[class*=hide], img[class*=hide], p[class*=hide], span[class*=hide] { display:none !important; }\n"
                + "table[class=h0], td[class=h0] { height: 0 !important; }\n"
                + "p[class=footer-content-left] { text-align: center !important; }\n"
                + "#headline p { font-size: 30px !important; }\n"
                + ".article-content, #left-sidebar{ -webkit-text-size-adjust: 90% !important; -ms-text-size-adjust: 90% !important; }\n"
                + ".header-content, .footer-content-left {-webkit-text-size-adjust: 80% !important; -ms-text-size-adjust: 80% !important;}\n"
                + "img { height: auto; line-height: 100%;}\n"
                + " } \n"
                + "/* Client-specific Styles */\n"
                + "#outlook a { padding: 0; }	/* Force Outlook to provide a \"view in browser\" button. */\n"
                + "body { width: 100% !important; }\n"
                + ".ReadMsgBody { width: 100%; }\n"
                + ".ExternalClass { width: 100%; display:block !important; } /* Force Hotmail to display emails at full width */\n"
                + "/* Reset Styles */\n"
                + "/* Add 100px so mobile switch bar doesn't cover street address. */\n"
                + "body { background-color: #ececec; margin: 0; padding: 0; }\n"
                + "img { outline: none; text-decoration: none; display: block;}\n"
                + "br, strong br, b br, em br, i br { line-height:100%; }\n"
                + "h1, h2, h3, h4, h5, h6 { line-height: 100% !important; -webkit-font-smoothing: antialiased; }\n"
                + "h1 a, h2 a, h3 a, h4 a, h5 a, h6 a { color: blue !important; }\n"
                + "h1 a:active, h2 a:active,  h3 a:active, h4 a:active, h5 a:active, h6 a:active {	color: red !important; }\n"
                + "/* Preferably not the same color as the normal header link color.  There is limited support for psuedo classes in email clients, this was added just for good measure. */\n"
                + "h1 a:visited, h2 a:visited,  h3 a:visited, h4 a:visited, h5 a:visited, h6 a:visited { color: purple !important; }\n"
                + "/* Preferably not the same color as the normal header link color. There is limited support for psuedo classes in email clients, this was added just for good measure. */  \n"
                + "table td, table tr { border-collapse: collapse; }\n"
                + ".yshortcuts, .yshortcuts a, .yshortcuts a:link,.yshortcuts a:visited, .yshortcuts a:hover, .yshortcuts a span {\n"
                + "color: black; text-decoration: none !important; border-bottom: none !important; background: none !important;\n"
                + "}	/* Body text color for the New Yahoo.  This example sets the font of Yahoo's Shortcuts to black. */\n"
                + "/* This most probably won't work in all email clients. Don't include code blocks in email. */\n"
                + "code {\n"
                + "  white-space: normal;\n"
                + "  word-break: break-all;\n"
                + "}\n"
                + "#background-table { background-color: #ececec; }\n"
                + "/* Webkit Elements */\n"
                + "#top-bar { border-radius:6px 6px 0px 0px; -moz-border-radius: 6px 6px 0px 0px; -webkit-border-radius:6px 6px 0px 0px; -webkit-font-smoothing: antialiased; background-color: #4b4044; color: #72a6a6; }\n"
                + "#top-bar a { font-weight: bold; color: #72a6a6; text-decoration: none;}\n"
                + "#footer { border-radius:0px 0px 6px 6px; -moz-border-radius: 0px 0px 6px 6px; -webkit-border-radius:0px 0px 6px 6px; -webkit-font-smoothing: antialiased; }\n"
                + "/* Fonts and Content */\n"
                + "body, td { font-family: HelveticaNeue, sans-serif; }\n"
                + ".header-content, .footer-content-left, .footer-content-right { -webkit-text-size-adjust: none; -ms-text-size-adjust: none; }\n"
                + "/* Prevent Webkit and Windows Mobile platforms from changing default font sizes on header and footer. */\n"
                + ".header-content { font-size: 12px; color: #72a6a6; }\n"
                + ".header-content a { font-weight: bold; color: #72a6a6; text-decoration: none; }\n"
                + "#headline p { color: #72a6a6; font-family: HelveticaNeue, sans-serif; font-size: 36px; text-align: center; margin-top:0px; margin-bottom:30px; }\n"
                + "#headline p a { color: #72a6a6; text-decoration: none; }\n"
                + ".article-title { font-size: 18px; line-height:24px; color: #d9653b; font-weight:bold; margin-top:0px; margin-bottom:18px; font-family: HelveticaNeue, sans-serif; }\n"
                + ".article-title a { color: #d9653b; text-decoration: none; }\n"
                + ".article-title.with-meta {margin-bottom: 0;}\n"
                + ".article-meta { font-size: 13px; line-height: 20px; color: #ccc; font-weight: bold; margin-top: 0;}\n"
                + ".article-content { font-size: 13px; line-height: 18px; color: #444444; margin-top: 0px; margin-bottom: 18px; font-family: HelveticaNeue, sans-serif; }\n"
                + ".article-content a { color: #7f8c4f; font-weight:bold; text-decoration:none; }\n"
                + ".article-content img { max-width: 100% }\n"
                + ".article-content ol, .article-content ul { margin-top:0px; margin-bottom:18px; margin-left:19px; padding:0; }\n"
                + ".article-content li { font-size: 13px; line-height: 18px; color: #444444; }\n"
                + ".article-content li a { color: #7f8c4f; text-decoration:underline; }\n"
                + ".article-content p {margin-bottom: 15px;}\n"
                + ".footer-content-left { font-size: 12px; line-height: 15px; color: #72a6a6; margin-top: 0px; margin-bottom: 15px; }\n"
                + ".footer-content-left a { color: #d5e9e7; font-weight: bold; text-decoration: none; }\n"
                + ".footer-content-right { font-size: 11px; line-height: 16px; color: #72a6a6; margin-top: 0px; margin-bottom: 15px; }\n"
                + ".footer-content-right a { color: #d5e9e7; font-weight: bold; text-decoration: none; }\n"
                + "#footer { background-color: #4b4c44; color: #72a6a6; }\n"
                + "#footer a { color: #d5e9e7; text-decoration: none; font-weight: bold; }\n"
                + "#permission-reminder { white-space: normal; }\n"
                + "#street-address { color: #d5e9e7; white-space: normal; }\n"
                + "</style>\n"
                + "<!--[if gte mso 9]>\n"
                + "<style _tmplitem=\"483\" >\n"
                + ".article-content ol, .article-content ul {\n"
                + "   margin: 0 0 0 24px;\n"
                + "   padding: 0;\n"
                + "   list-style-position: inside;\n"
                + "}\n"
                + "</style>\n"
                + "<![endif]--></head><body><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"background-table\">\n"
                + "	<tbody><tr>\n"
                + "		<td align=\"center\" bgcolor=\"#ececec\">\n"
                + "        	<table class=\"w640\" style=\"margin:0 10px;\" width=\"640\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                + "            	<tbody><tr><td class=\"w640\" width=\"640\" height=\"20\"></td></tr>\n"
                + "                \n"
                + "            	<tr>\n"
                + "                	<td class=\"w640\" width=\"640\">\n"
                + "                        <table id=\"top-bar\" class=\"w640\" width=\"640\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#FFFFFF\">\n"
                + "    <tbody><tr>\n"
                + "        <td class=\"w15\" width=\"15\"></td>\n"
                + "        <td class=\"w325\" width=\"350\" valign=\"middle\" align=\"left\">\n"
                + "            <table class=\"w325\" width=\"350\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                + "                <tbody><tr><td class=\"w325\" width=\"350\" height=\"8\"></td></tr>\n"
                + "            </tbody></table>\n"
                + "            <div class=\"header-content\"></span></div>\n"
                + "            <table class=\"w325\" width=\"350\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                + "                <tbody><tr><td class=\"w325\" width=\"350\" height=\"8\"></td></tr>\n"
                + "            </tbody></table>\n"
                + "        </td>\n"
                + "        <td class=\"w30\" width=\"30\"></td>\n"
                + "        <td class=\"w255\" width=\"255\" valign=\"middle\" align=\"right\">\n"
                + "            <table class=\"w255\" width=\"255\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                + "                <tbody><tr><td class=\"w255\" width=\"255\" height=\"8\"></td></tr>\n"
                + "            </tbody></table>\n"
                + "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                + "    <tbody><tr>\n"
                + "        \n"
                + "        \n"
                + "        \n"
                + "    </tr>\n"
                + "</tbody></table>\n"
                + "            <table class=\"w255\" width=\"255\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                + "                <tbody><tr><td class=\"w255\" width=\"255\" height=\"8\"></td></tr>\n"
                + "            </tbody></table>\n"
                + "        </td>\n"
                + "        <td class=\"w15\" width=\"15\"></td>\n"
                + "    </tr>\n"
                + "</tbody></table>\n"
                + "                        \n"
                + "                    </td>\n"
                + "                </tr>\n"
                + "                <tr>\n"
                + "                <td id=\"header\" class=\"w640\" width=\"640\" align=\"center\" bgcolor=\"#FFFFFF\">\n"
                + "    \n"
                + "    <div align=\"center\" style=\"text-align: center\">\n"
                + "        <a href=\"http://www.google.com.ar\">\n"
                + "        <img id=\"customHeaderImage\" label=\"Header Image\" editable=\"true\" width=\"550\" src=\"cid:image\" class=\"w640\" border=\"0\" align=\"top\" style=\"display: inline\">\n"
                + "        </a>\n"
                + "    </div>\n"
                + "    \n"
                + "    \n"
                + "</td>\n"
                + "                </tr>\n"
                + "                \n"
                + "                <tr><td class=\"w640\" width=\"640\" height=\"30\" bgcolor=\"#ffffff\"></td></tr>\n"
                + "                <tr id=\"simple-content-row\"><td class=\"w640\" width=\"640\" bgcolor=\"#ffffff\">\n"
                + "    <table align=\"left\" class=\"w640\" width=\"640\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                + "        <tbody><tr>\n"
                + "            <td class=\"w30\" width=\"30\"></td>\n"
                + "            <td class=\"w580\" width=\"580\">");
    }

    private void buildFooterContent() {
        getBuilder().append("            </td>\n"
                + "            <td class=\"w30\" width=\"30\"></td>\n"
                + "        </tr>\n"
                + "    </tbody></table>\n"
                + "</td></tr>\n"
                + "                <tr><td class=\"w640\" width=\"640\" height=\"15\" bgcolor=\"#ffffff\"></td></tr>\n"
                + "                \n"
                + "                <tr>\n"
                + "                <td class=\"w640\" width=\"640\">\n"
                + "    <table id=\"footer\" class=\"w640\" width=\"640\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#4b4c44\">\n"
                + "        <tbody><tr><td class=\"w30\" width=\"30\"></td><td class=\"w580 h0\" width=\"360\" height=\"30\"></td><td class=\"w0\" width=\"60\"></td><td class=\"w0\" width=\"160\"></td><td class=\"w30\" width=\"30\"></td></tr>\n"
                + "        <tr>\n"
                + "            <td class=\"w30\" width=\"30\"></td>\n"
                + "            <td class=\"w580\" width=\"360\" valign=\"top\">\n"
                + "            <span class=\"hide\"><p id=\"permission-reminder\" align=\"left\" class=\"footer-content-left\"></p></span>\n"
                + "            <p align=\"left\" class=\"footer-content-left\"></p>\n"
                + "            </td>\n"
                + "            <td class=\"hide w0\" width=\"60\"></td>\n"
                + "            <td class=\"hide w0\" width=\"160\" valign=\"top\">\n"
                + "            <p id=\"street-address\" align=\"right\" class=\"footer-content-right\"></p>\n"
                + "            </td>\n"
                + "            <td class=\"w30\" width=\"30\"></td>\n"
                + "        </tr>\n"
                + "        <tr><td class=\"w30\" width=\"30\"></td><td class=\"w580 h0\" width=\"360\" height=\"15\"></td><td class=\"w0\" width=\"60\"></td><td class=\"w0\" width=\"160\"></td><td class=\"w30\" width=\"30\"></td></tr>\n"
                + "    </tbody></table>\n"
                + "</td>\n"
                + "                </tr>\n"
                + "                <tr><td class=\"w640\" width=\"640\" height=\"60\"></td></tr>\n"
                + "            </tbody></table>\n"
                + "        </td>\n"
                + "	</tr>\n"
                + "</tbody></table></body></html>");
    }

    private void addVariableContent() {
        builder.append("<layout label=\"Text only\">\n"
                + "                        <table class=\"w580\" width=\"580\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                + "                            <tbody><tr>\n"
                + "                                <td class=\"w580\" width=\"580\">\n"
                + "                                    <p align=\"left\" class=\"article-title\"><singleline label=\"Title\">");
        builder.append(title);
        builder.append("</singleline></p>\n"
                + "                                    <div align=\"left\" class=\"article-content\">\n"
                + "                                        <multiline label=\"Description\">");
        builder.append(content);
        builder.append("</multiline>\n"
                + "                                    </div>\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                            <tr><td class=\"w580\" width=\"580\" height=\"10\"></td></tr>\n"
                + "                        </tbody></table>\n"
                + "                    </layout>");
    }

    protected abstract void setVariableContent();

    /**
     * @return the builder
     */
    public StringBuilder getBuilder() {
        return builder;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
