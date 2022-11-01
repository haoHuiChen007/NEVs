package com.example.nevs.util;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
public final class IPUtil {
   private IPUtil(){}
   private static final Logger log = LoggerFactory.getLogger(IPUtil.class);
   private static final Map<String, String> subnetMaskMap = new HashMap<String, String>();
   static{
      subnetMaskMap.put("8", "255.0.0.0");
      subnetMaskMap.put("16", "255.255.0.0");
      subnetMaskMap.put("24", "255.255.255.0");
      subnetMaskMap.put("128", "(::1/128");
      subnetMaskMap.put("10", "fe80::203:baff:fe27:1243/10");
   }
 
   /**
    * 获取客户端IP
    */
   public static String getClientIP(HttpServletRequest request){
      String IP = request.getHeader("x-forwarded-for");
      if(null==IP || 0==IP.length() || "unknown".equalsIgnoreCase(IP)){
         IP = request.getHeader("Proxy-Client-IP");
      }
      if(null==IP || 0==IP.length() || "unknown".equalsIgnoreCase(IP)){
         IP = request.getHeader("WL-Proxy-Client-IP");
      }
      if(null==IP || 0==IP.length() || "unknown".equalsIgnoreCase(IP)){
         IP = request.getRemoteAddr();
      }
      //对于通过多个代理的情况,第一个IP为客户端真实IP
      //多个IP会按照','分割('***.***.***.***'.length()=15)
      if(null!=IP && IP.length()>15){
         if(IP.indexOf(",") > 0){
            IP = IP.substring(0, IP.indexOf(","));
         }
      }
      return IP;
   }
 
 
   /**
    * 获取客户端IP的另一种写法
    */
   @Deprecated
   public static String getClientIP_(HttpServletRequest request){
      String IP = null;
      Enumeration<String> enu = request.getHeaderNames();
      while(enu.hasMoreElements()){
         String name = enu.nextElement();
         if(name.equalsIgnoreCase("X-Forwarded-For")){
            IP = request.getHeader(name);
         }else if(name.equalsIgnoreCase("Proxy-Client-IP")){
            IP = request.getHeader(name);
         }else if(name.equalsIgnoreCase("WL-Proxy-Client-IP")){
            IP = request.getHeader(name);
         }
         if(null!=IP && 0!=IP.length()){
            break;
         }
      }
      if(null==IP || 0==IP.length()){
         IP = request.getRemoteAddr();
      }
      return IP;
   }
 
 
   /**
    * 获取服务器IP的另一种写法
    */
   @Deprecated
   public static String getServerIP_(){
      StringBuilder serverIP = new StringBuilder();
      try{
         Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
         while(nets.hasMoreElements()){
            NetworkInterface net = nets.nextElement();
            Enumeration<InetAddress> addresses = net.getInetAddresses();
            while(addresses.hasMoreElements()){
               InetAddress IP = addresses.nextElement();
               if(IP instanceof Inet4Address && !IP.getHostAddress().equals("127.0.0.1")){
                  serverIP.append(IP.toString());
               }
            }
         }
      }catch(SocketException e){
         log.error("服务器IP地址获取失败,堆栈轨迹如下", e);
         serverIP = new StringBuilder("服务器IP地址获取失败");
      }
      return serverIP.toString();
   }
 
 
   /**
    * 获取服务器IP
    */
   public static String getServerIP(){
      String serverIP = "";
      try{
         Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
         tag:while(nets.hasMoreElements()){
            NetworkInterface net = nets.nextElement();
            if(null != net.getHardwareAddress()){
               List<InterfaceAddress> addressList = net.getInterfaceAddresses();
               for(InterfaceAddress obj : addressList){
                  InetAddress IP = obj.getAddress();
                  if(IP instanceof Inet4Address && !IP.getHostAddress().equals("127.0.0.1")){
                     System.out.println("IP=[" + IP.getHostAddress() +"]的子网掩码为=[" + subnetMaskMap.get(String.valueOf(obj.getNetworkPrefixLength())) + "]");
                     serverIP = IP.getHostAddress();
                     break tag;
                  }
               }
            }
         }
      }catch(SocketException e){
         log.error("服务器IP地址获取失败,堆栈轨迹如下", e);
         serverIP = "服务器IP地址获取失败";
      }
      return serverIP;
   }
}