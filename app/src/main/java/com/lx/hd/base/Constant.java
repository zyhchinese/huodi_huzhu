package com.lx.hd.base;

public class Constant {

    //  public static final String BASE_URL ="http://192.168.1.231:8080/showtime/";
  public static final String BASE_URL ="http://192.168.1.236:8280/hdsy/";
//  public static final String BASE_URL ="http://118.190.47.231:8004/hdsy/";
//  public static final String BASE_URL ="http://www.huodiwulian.com/";
//  public static final String BASE_URL ="http://192.168.1.57:7099/lxnew/";
//  public static final String BASE_URL ="http://192.168.1.55/hdsy/";
//  public static final String BASE_URL ="http://192.168.1.45:8080/maibate2/";
//    public static final String BASE_URL = "http://www.maibat.com/maibate/";
    public static final String PAY_URL = "http://www.maibat.com/";
    public static final String BASE_WEBSOCKET_URL = "ws://www.maibat.com/maibate/pcwebsocket";
    //   public static final String BASE_URL ="http://47.93.28.241/maibate/";
//    public static final String BASE_URL = "http://192.168.1.45:8080/maibate2/";
    //    public static final String BASE_URL ="http://192.168.1.55:80/maibate/";
    public static final String HOLD_ACCOUNT = "hold_account";
    public static final int REQUEST_CODE_CAMERA = 1023;//调用相机
    public static final int REQUEST_CODE = 1024;//获取相册
    public static final int COMPRESS_REQUEST_CODE = 2048;
    public static String city = "";
    public static String wl_jgmxcity = "";//物流价格明细地址
    public static boolean cityflag = false;
    public static String WXPAY_STARTNAME = "";//调用微信支付的调用方名字，用于区分广播
    public static String HUOYUAN_SHENG="";
    public static String HUOYUAN_SHI="";
    /**
     * 支付宝支付业务：入参app_id
     */
//    public static final String APPID = "2017090508564772";
    public static final String APPID = "2018022402261441";

    // 商户PID
    public static final String PARTNER = "2088821027321059";
    // 商户收款账号
    public static final String SELLER = "dingyuru@maibut.com";
    // 商户私钥，pkcs8格式
//    public static final String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCWV51I3JmT70UsQzW3ramMgeeJdhGlhAKeZjtTu4pJtzxb4mT41XIvppKHEeYS6N50RRAfT57GbD9zzpV1RCkSGgefPwLcslUiyFAykdA8bk8XT2awGUJZZ5kgCxqLDY0nxd+QESIhLaWTjIqheNf/AP9AMHauAFYna5MWfbWsPGWLDz77hN8tlHBu6C1IGVgFPunCcXypk40nCy2TYbSiPrsvx/e6Ui8T9ylR/NOZS55kI8IsEabl4kcm7lS4mclrSVMBFr+6l8E6P0el6H3SgVO59QeCgyphksS178ngi5umA6h2n+h+xQugIzPbfWlkn1yqiJKQeaiF+JR/ZorfAgMBAAECggEABoPVdQraPObpgHmJImSMLGKUvgg3y4xk8KhNedtuqrMeEn1FEuNtB1OYlfHYnoko2rEDedfhcYfPWB1jrKk/fmFSYzw/y4CO3+r+TrKy62t8Ue5G7OqrTWSH5jOU+uGjdE3G2l9jszxfKH22pDiwY4SPiyCOhAvPr/WhciAQd141oq/tpplbQSw+cSjzogrvojKQHeL7KVSNqSu3Fki0Hs2fk2SV/KLdY/BUUUoBK09l6T/eYuWCtH/usCGDwy7tEvZXgLfaM4VnrkZ9z7SdPkHCbCnbejppR9KmJPIh02AOSUpnTOqPY+1G1IZivgbRdouwG+kX9vZOPc22DOtHgQKBgQDNnH5SOm5BMD4wo75E49R9F4sZbku1yeEgNVPb0CYh4PjwozzGn8RJWcYv29g16DdRHKLt1BNEB9+iAHSiZyewitD0FXQ+KFa0Kat9B275cblYVLunKjX+ShWrv8FgijOpkah+g/Ou30ox6WmDRxzJWYoLvWmEkkubp5nnrE7meQKBgQC7L6/nHjQEKV7v+4iqGtfQXrinLxdChNxXg/sVAf1W8ibtyugXwQ7L3+U7qMXtQfmClZXGZJ9p5bR54IH/xr88CrVxwSV88MHYFztyFT3wScuGwpW2gwnKicsplYZ/6rnefNt4nLDigMWZXv+0gjVnT9jc3G1O46dzno3sC/EGFwKBgFQ3I/pUGnKy6tYLS1R2KMNv0DaDWZlE7eO+U/G1qNi5h90wTyVfrQsKLUXO+xjhWz7qxsU41wdHXk7BdwRJ0hTaVsmSvAD1jsXOR4I3eDnNXcgTNKU8gc780zF8oh0DnjN3CJeBPl9C7+XPn7r4do72ELfMRhrZvQQtOrmMduipAoGAOgg4wrBsc/XXhxM2dXZI/kK/gKVq0qaaIU+7ofGx9yivxP0pI2QpEC/jw7E5W6sejcuOWamMeqpKX5ao9wFI/HZddlzpIGkLz9C0D1RilYJrZOYiwCiz+mTp5YSD5FiDM2UGLch2VmKR4FDBedb8c4EfvKliAhk2KvQ3D71MBhUCgYEAh4q9gD+Z13Udn6esNH9Ns43xRlHZalyb/HzbL0scLCqbz0ksNMZjWtSQ1WwJ9zK5etKZKNqUuCOlYgyQ9I6/QrBbIXRT9DB+TmR0kCy9V/MnE9ynJRFQ+lE5fbYAJmP6Tr70E0SLhSmFh1Ys01HHNEVF1NsedavrZVp4HUmaImY=";
    public static final String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCZ6xUj91M9ESuYcKcHSNbUE+A1SwnfHtJAeI4DRiRJp8Qv4B9zPhunrGglUHdo2+IOkr+U6zErkeUnRgo9L92X0fEqzyk+aMp76lG9YHPVriA8HestIWrq/hycUwe22ONcrEQ/WuPF7apgM9XSHSyiP9GZqFHYLW0+WMLmmEFpa/08J9WH429Bu07xtzwo90imiYhgJPvuk8KQGPZ7bVZsbcIVxHcsphlGm9MCXGEbazbWgPbC8AxTbxa1WLtGLbz74TJ4fWNaE+L9MjuVx4RmRL4ES+HE8BqzJfEbcekGKrwFVQ54C44cfQfrLbrR3+Hb4LprPDymDEitPTVgrtVxAgMBAAECggEAHy/JzcrMO+Bhw/Z3cXtpw/jvAf1dV68oDNYgeDtbGO18S/MOjEw2UEgXpg/i0gVTjX5ggS0SfnCGTOndIz6ymJN6WSE2iyWyeV8mBsgnUFGCyIuLXk6aGlk4tNgCBSN3Fgz1mxywcCdS0UFPfs5Yf9uMfaTkSHgT3YEgpJaPEmv1dmkFCDDt/8AONz6JQxl3Sbf4wHfGBybnjZ2gZSm6VKqfWmn+hZBkjHO4Mk2SNdRYaq4YB9XZ7groXaoTC6LfPeWqI3gsyjf9hhIFZVrHcEq2nI60CwA1l5TNMMag1652nl2bHIH2oEEoNn1IejlJDf6/1hKy/S32dHVquBUJ3QKBgQDUs2cuHeQRqW1rT36uaEA1vSExG7g/1vX/8XWASs5yvMwl7VcUjdS/OBRN9GZEwdmO+xjoA3m/CFkvQ0l033Q6ZcZ1KqKE8OKLiQW/nN/HV7FQKa0nnUjI23FT/MOUlIIetUf3dz0dpSzVbBmC1Izrt+D1OpR2dkHj7VHoJ4PptwKBgQC5QFBPLAl2wnclXicxeS15rqQ69Tfh9O937Ky3FergPzMa2x0Sbho96b2fRd+pHTxS9Ctpr6zdYiBnLJwcSH9ZWpzUm/wjenh3FrhXWuUQEa1DpeMYvcBosRU+BuAdTSOgDbWTRUNceFHnbrigfSU/JKOaTHbJQRqKpKlnc6naFwKBgBrgRGReYIgeyXWqyM4O3DYuHLiQ0Ro39kON5hP839M9qeoM8Fjnhv9WfbJP9HAxsmTPs5j1f3e0/Vfsf6al6ZDLdWUuqvRzX5zUBobsspYEraH8ciW0SRMgL1S2dpwam8p3i41oYq+RRHX/P8X5R4ktZd52tXW2gaLkRNo5zABHAoGAXBKwF7medo1O5J64QimlRY5ciAUrIj4361YRvenB1HT09IslFuzsVAthuNDkykwCmIRNM3kiJOJJkp4kdRY/XWzg9/cTSZrsVtxPAPPnV6ZBhJblmX1uLVqeYTbPmyCk7T8DGVBam5IduzZXw0SQB6MxQVZxz7Nw+qm+nPFq4T8CgYEAjVrrn6Giv3Wevbt12ZmtlQ89y9jpf4fmXOck7/gnmK9E/vTLyeHINuhvFR5Zmab7RgIE+GaWpYqrP/QRD8kWNrlpZNnMWB1aCXOZU4LMZfvknapxxJZPkI3iegycyqPTEZhjYrebc6iaEhplVegdOno7f+RaqzXlcZrKHKDmHbY=";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBjLZJco3R1qjPehkzJOWAcExynm6oOe2mNSmscciXSMnwYOBCCdJXF/FAvyfzi8dcbxkbW8EF6ygAFnUAMjEw+wSQ2Wh/8kw7x9KM81CpFgWyjGSX6ZyHw+y6oiQDL4zrlTkIeecVkpJUmZbX0a7DT9wsZCov19qNhfoP4ywnEwIDAQAB";


    // APP_ID 替换为你的应用从官方网站申请到的合法appId
//    public static final String APP_ID = "wx9d147fb6eec962ec";
    public static final String APP_ID = "wx2303e3c37663ae5c";
    //  API密钥，在商户平台设置
//    public static final String API_KEY = "Mwe3Bg0To2mYYo00o1fe31y4IDy8em13";
    public static final String API_KEY = "hRky5X7gBIFJ5YquJKTH08NtK0fH2RQ4";
    //商户号 微信分配的公众账号ID
//    public static final String MCH_ID = "1488915852";
    public static final String MCH_ID = "1503298331";
}
