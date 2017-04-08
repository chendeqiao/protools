package pro.tools.security;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * HTTPS组件
 */
public abstract class ToolHTTPS2 {

    /**
     * 获得KeyStore
     *
     * @param keyStorePath 密钥库路径
     * @param password     密码
     * @return KeyStore 密钥库
     * @throws Exception
     */
    private static KeyStore getKeyStore(String keyStorePath, String password) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {

        // 实例化密钥库
        KeyStore ks = KeyStore.getInstance("PKCS12");
        // KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());

        // 获得密钥库文件流
        FileInputStream is = new FileInputStream(keyStorePath);

        // 加载密钥库
        ks.load(is, password.toCharArray());

        // 关闭密钥库文件流
        is.close();

        return ks;
    }

    /**
     * 获得SSLSocektFactory
     *
     * @param password       密码
     * @param keyStorePath   密钥库路径
     * @param trustStorePath 信任库路径
     * @return SSLSocketFactory
     * @throws Exception
     */
    private static SSLSocketFactory getSSLSocketFactory(String password, String keyStorePath, String trustStorePath) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        return ToolHTTPS.getSSLSocketFactory(password, keyStorePath, trustStorePath);
    }

    /**
     * 为HttpsURLConnection配置SSLSocketFactory
     *
     * @param conn              HttpsURLConnection
     * @param password          密码
     * @param keyStorePath      密钥库路径
     * @param trustKeyStorePath 信任库路径
     * @throws Exception
     */
    public static void configSSLSocketFactory(HttpsURLConnection conn, String password, String keyStorePath,
                                              String trustKeyStorePath) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        // 获得SSLSocketFactory
        SSLSocketFactory sslSocketFactory = getSSLSocketFactory(password, keyStorePath, trustKeyStorePath);

        // 设置SSLSocketFactory
        conn.setSSLSocketFactory(sslSocketFactory);
    }

}