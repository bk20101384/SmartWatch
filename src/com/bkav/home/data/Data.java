package com.bkav.home.data;

/**
 * Data Ä‘Æ°á»£c sá»­ dá»¥ng Ä‘á»ƒ truy cáº­p cÃ¡c Ä‘á»‘i tÆ°á»£ng sá»­ dá»¥ng trong trao Ä‘á»•i 
 * dá»¯ liá»‡u, vá»›i má»¥c Ä‘Ã­ch Ä‘Æ°a ra má»™t phÆ°Æ¡ng thá»©c Ä‘á»‹nh dáº¡ng dá»¯ liá»‡u Ä‘Æ¡n giáº£n, 
 * hiá»‡u quáº£ Ä‘á»ƒ trao Ä‘á»•i dá»¯ liá»‡u.
 * <p>
 * Má»™t Ä‘á»‘i tÆ°á»£ng bao gá»“m cÃ¡c cáº·p vá»›i tÃªn/giÃ¡ trá»‹, trong Ä‘Ã³ tÃªn 
 * lÃ  má»™t xÃ¢u kÃ½ tá»±, giÃ¡ trá»‹ cÃ³ thá»ƒ lÃ  má»™t xÃ¢u kÃ½ tá»±, má»™t Ä‘á»‘i tÆ°á»£ng hoáº·c má»™t
 * máº£ng cÃ¡c giÃ¡ trá»‹. Ä�á»‘i tÆ°á»£ng Ä‘Æ°á»£c biá»ƒu diá»…n trong cáº·p ngoáº·c <code>{}</code>,
 * cÃ¡c cáº·p Ä‘Æ°á»£c biá»ƒu diá»…n dÆ°á»›i dáº¡ng <code>name:value</code> vÃ  cÃ¡ch nhau 
 * báº±ng dáº¥u pháº£y.  
 * <p>
 * Má»™t máº£ng bao gá»“m khÃ´ng, má»™t hoáº·c nhiá»�u pháº§n tá»­, má»—i pháº§n tá»­ lÃ  má»™t Ä‘á»‘i
 * tÆ°á»£ng, má»™t xÃ¢u kÃ½ tá»±, hoáº·c má»™t máº£ng. Máº£ng Ä‘Æ°á»£c biá»ƒu diá»…n trong cáº·p ngoáº·c
 * vuÃ´ng <code>[]</code>, cÃ¡c pháº§n tá»­ phÃ¢n cÃ¡ch nhau báº±ng dáº¥u pháº£y.
 * <p>
 * Má»™t xÃ¢u kÃ½ tá»± lÃ  má»™t táº­p cÃ¡c kÃ½ tá»± báº¥t ká»³, trong Ä‘Ã³ Ä‘á»ƒ biá»ƒu diá»…n cÃ¡c kÃ½
 * tá»± <code>{}[]:,\</code>, sá»­ dá»¥ng kÃ½ tá»± <code>\</code> trÆ°á»›c.
 * <p>
 * VÃ­ dá»¥:
 * <pre>
 * {address:12,register:{16:0,17:1},input:[relay,motion]}
 * </pre>
 * Data sá»­ dá»¥ng Ä‘á»ƒ biá»ƒu diá»…n má»™t giÃ¡ trá»‹ cÃ³ thá»ƒ lÃ  má»™t Ä‘á»‘i tÆ°á»£ng, 
 * má»™t xÃ¢u kÃ½ tá»± hoáº·c má»™t máº£ng.
 * 
 * @author Pháº¡m Quang HoÃ 
 */
public interface Data {
	/**
	 * Danh sÃ¡ch tÃªn cÃ¡c thuá»™c tÃ­nh. Thá»© tá»± cÃ¡c tÃªn trong danh sÃ¡ch nÃ y khÃ´ng 
	 * nháº¥t thiáº¿t pháº£i theo thá»© tá»± cÃ¡c tÃªn trong dá»¯ liá»‡u nguá»“n.
	 * @return Máº£ng chá»©a tÃªn cÃ¡c thuá»™c tÃ­nh, hoáº·c máº£ng rá»—ng náº¿u Ä‘á»‘i tÆ°á»£ng khÃ´ng
	 * cÃ³ thuá»™c tÃ­nh nÃ o.
	 */
	public String[] getNames();
	
	/**
	 * XÃ¢u kÃ½ tá»± tÆ°Æ¡ng á»©ng vá»›i tÃªn.
	 * @param name TÃªn thuá»™c tÃ­nh.
	 * @return XÃ¢u kÃ½ tá»± biá»ƒu diá»…n giÃ¡ trá»‹ thuá»™c tÃ­nh, hoáº·c xÃ¢u rá»—ng náº¿u thuá»™c 
	 * tÃ­nh khÃ´ng pháº£i lÃ  xÃ¢u kÃ½ tá»±, hoáº·c khÃ´ng tá»“n táº¡i thuá»™c tÃ­nh cÃ³ tÃªn 
	 * tÆ°Æ¡ng á»©ng.
	 */
	public String getString(String name);
	
	/**
	 * Thuá»™c tÃ­nh tÆ°Æ¡ng á»©ng vá»›i tÃªn. Trong trÆ°á»�ng há»£p thuá»™c tÃ­nh lÃ  má»™t
	 *  máº£ng cÃ¡c giÃ¡ trá»‹, hÃ m tráº£ vá»� pháº§n tá»­ Ä‘áº§u tiÃªn trong máº£ng. Ä�á»ƒ láº¥y cÃ¡c 
	 *  pháº§n tá»­ tiáº¿p theo trong máº£ng, gá»�i phÆ°Æ¡ng thá»©c <code>getNext</code> 
	 *  cá»§a pháº§n tá»­ trong máº£ng.
	 *    
	 * @param name TÃªn thuá»™c tÃ­nh.
	 * @return GiÃ¡ trá»‹ thuá»™c tÃ­nh, hoáº·c <code>null</code> náº¿u khÃ´ng tá»“n táº¡i 
	 * thuá»™c tÃ­nh cÃ³ tÃªn tÆ°Æ¡ng á»©ng.
	 */
	
	public Data getObject(String name); 
	
	/**
	 * Pháº§n tá»­ tiáº¿p theo trong máº£ng.
	 * @return Pháº§n tá»­ tiáº¿p theo trong máº£ng, hoáº·c <code>null</code> náº¿u khÃ´ng
	 * tá»“n táº¡i pháº§n tá»­ tiáº¿p theo.
	 */
	
	public Data getNext();
}