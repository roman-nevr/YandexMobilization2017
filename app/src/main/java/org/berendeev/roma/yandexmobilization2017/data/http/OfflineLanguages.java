package org.berendeev.roma.yandexmobilization2017.data.http;

import java.util.HashMap;
import java.util.Map;

public class OfflineLanguages {

    public static final String RU = "ru";
    public static final String EN = "en";
    public static final String UK = "uk";
    public static final String TR = "tr";

    private static String ru = "{\"dirs\":[\"az-ru\",\"be-bg\",\"be-cs\",\"be-de\",\"be-en\",\"be-es\",\"be-fr\",\"be-it\",\"be-pl\",\"be-ro\",\"be-ru\",\"be-sr\",\"be-tr\",\"bg-be\",\"bg-ru\",\"bg-uk\",\"ca-en\",\"ca-ru\",\"cs-be\",\"cs-en\",\"cs-ru\",\"cs-uk\",\"da-en\",\"da-ru\",\"de-be\",\"de-en\",\"de-es\",\"de-fr\",\"de-it\",\"de-ru\",\"de-tr\",\"de-uk\",\"el-en\",\"el-ru\",\"en-be\",\"en-ca\",\"en-cs\",\"en-da\",\"en-de\",\"en-el\",\"en-es\",\"en-et\",\"en-fi\",\"en-fr\",\"en-hu\",\"en-it\",\"en-lt\",\"en-lv\",\"en-mk\",\"en-nl\",\"en-no\",\"en-pt\",\"en-ru\",\"en-sk\",\"en-sl\",\"en-sq\",\"en-sv\",\"en-tr\",\"en-uk\",\"es-be\",\"es-de\",\"es-en\",\"es-ru\",\"es-uk\",\"et-en\",\"et-ru\",\"fi-en\",\"fi-ru\",\"fr-be\",\"fr-de\",\"fr-en\",\"fr-ru\",\"fr-uk\",\"hr-ru\",\"hu-en\",\"hu-ru\",\"hy-ru\",\"it-be\",\"it-de\",\"it-en\",\"it-ru\",\"it-uk\",\"lt-en\",\"lt-ru\",\"lv-en\",\"lv-ru\",\"mk-en\",\"mk-ru\",\"nl-en\",\"nl-ru\",\"no-en\",\"no-ru\",\"pl-be\",\"pl-ru\",\"pl-uk\",\"pt-en\",\"pt-ru\",\"ro-be\",\"ro-ru\",\"ro-uk\",\"ru-az\",\"ru-be\",\"ru-bg\",\"ru-ca\",\"ru-cs\",\"ru-da\",\"ru-de\",\"ru-el\",\"ru-en\",\"ru-es\",\"ru-et\",\"ru-fi\",\"ru-fr\",\"ru-hr\",\"ru-hu\",\"ru-hy\",\"ru-it\",\"ru-lt\",\"ru-lv\",\"ru-mk\",\"ru-nl\",\"ru-no\",\"ru-pl\",\"ru-pt\",\"ru-ro\",\"ru-sk\",\"ru-sl\",\"ru-sq\",\"ru-sr\",\"ru-sv\",\"ru-tr\",\"ru-uk\",\"sk-en\",\"sk-ru\",\"sl-en\",\"sl-ru\",\"sq-en\",\"sq-ru\",\"sr-be\",\"sr-ru\",\"sr-uk\",\"sv-en\",\"sv-ru\",\"tr-be\",\"tr-de\",\"tr-en\",\"tr-ru\",\"tr-uk\",\"uk-bg\",\"uk-cs\",\"uk-de\",\"uk-en\",\"uk-es\",\"uk-fr\",\"uk-it\",\"uk-pl\",\"uk-ro\",\"uk-ru\",\"uk-sr\",\"uk-tr\"],\"langs\":{\"af\":\"Африкаанс\",\"am\":\"Амхарский\",\"ar\":\"Арабский\",\"az\":\"Азербайджанский\",\"ba\":\"Башкирский\",\"be\":\"Белорусский\",\"bg\":\"Болгарский\",\"bn\":\"Бенгальский\",\"bs\":\"Боснийский\",\"ca\":\"Каталанский\",\"ceb\":\"Себуанский\",\"cs\":\"Чешский\",\"cy\":\"Валлийский\",\"da\":\"Датский\",\"de\":\"Немецкий\",\"el\":\"Греческий\",\"en\":\"Английский\",\"eo\":\"Эсперанто\",\"es\":\"Испанский\",\"et\":\"Эстонский\",\"eu\":\"Баскский\",\"fa\":\"Персидский\",\"fi\":\"Финский\",\"fr\":\"Французский\",\"ga\":\"Ирландский\",\"gd\":\"Шотландский (гэльский)\",\"gl\":\"Галисийский\",\"gu\":\"Гуджарати\",\"he\":\"Иврит\",\"hi\":\"Хинди\",\"hr\":\"Хорватский\",\"ht\":\"Гаитянский\",\"hu\":\"Венгерский\",\"hy\":\"Армянский\",\"id\":\"Индонезийский\",\"is\":\"Исландский\",\"it\":\"Итальянский\",\"ja\":\"Японский\",\"jv\":\"Яванский\",\"ka\":\"Грузинский\",\"kk\":\"Казахский\",\"km\":\"Кхмерский\",\"kn\":\"Каннада\",\"ko\":\"Корейский\",\"ky\":\"Киргизский\",\"la\":\"Латынь\",\"lb\":\"Люксембургский\",\"lo\":\"Лаосский\",\"lt\":\"Литовский\",\"lv\":\"Латышский\",\"mg\":\"Малагасийский\",\"mhr\":\"Марийский\",\"mi\":\"Маори\",\"mk\":\"Македонский\",\"ml\":\"Малаялам\",\"mn\":\"Монгольский\",\"mr\":\"Маратхи\",\"mrj\":\"Горномарийский\",\"ms\":\"Малайский\",\"mt\":\"Мальтийский\",\"my\":\"Бирманский\",\"ne\":\"Непальский\",\"nl\":\"Голландский\",\"no\":\"Норвежский\",\"pa\":\"Панджаби\",\"pap\":\"Папьяменто\",\"pl\":\"Польский\",\"pt\":\"Португальский\",\"ro\":\"Румынский\",\"ru\":\"Русский\",\"si\":\"Сингальский\",\"sk\":\"Словацкий\",\"sl\":\"Словенский\",\"sq\":\"Албанский\",\"sr\":\"Сербский\",\"su\":\"Сунданский\",\"sv\":\"Шведский\",\"sw\":\"Суахили\",\"ta\":\"Тамильский\",\"te\":\"Телугу\",\"tg\":\"Таджикский\",\"th\":\"Тайский\",\"tl\":\"Тагальский\",\"tr\":\"Турецкий\",\"tt\":\"Татарский\",\"udm\":\"Удмуртский\",\"uk\":\"Украинский\",\"ur\":\"Урду\",\"uz\":\"Узбекский\",\"vi\":\"Вьетнамский\",\"xh\":\"Коса\",\"yi\":\"Идиш\",\"zh\":\"Китайский\"}}";

    private static String en = "{\"dirs\":[\"az-ru\",\"be-bg\",\"be-cs\",\"be-de\",\"be-en\",\"be-es\",\"be-fr\",\"be-it\",\"be-pl\",\"be-ro\",\"be-ru\",\"be-sr\",\"be-tr\",\"bg-be\",\"bg-ru\",\"bg-uk\",\"ca-en\",\"ca-ru\",\"cs-be\",\"cs-en\",\"cs-ru\",\"cs-uk\",\"da-en\",\"da-ru\",\"de-be\",\"de-en\",\"de-es\",\"de-fr\",\"de-it\",\"de-ru\",\"de-tr\",\"de-uk\",\"el-en\",\"el-ru\",\"en-be\",\"en-ca\",\"en-cs\",\"en-da\",\"en-de\",\"en-el\",\"en-es\",\"en-et\",\"en-fi\",\"en-fr\",\"en-hu\",\"en-it\",\"en-lt\",\"en-lv\",\"en-mk\",\"en-nl\",\"en-no\",\"en-pt\",\"en-ru\",\"en-sk\",\"en-sl\",\"en-sq\",\"en-sv\",\"en-tr\",\"en-uk\",\"es-be\",\"es-de\",\"es-en\",\"es-ru\",\"es-uk\",\"et-en\",\"et-ru\",\"fi-en\",\"fi-ru\",\"fr-be\",\"fr-de\",\"fr-en\",\"fr-ru\",\"fr-uk\",\"hr-ru\",\"hu-en\",\"hu-ru\",\"hy-ru\",\"it-be\",\"it-de\",\"it-en\",\"it-ru\",\"it-uk\",\"lt-en\",\"lt-ru\",\"lv-en\",\"lv-ru\",\"mk-en\",\"mk-ru\",\"nl-en\",\"nl-ru\",\"no-en\",\"no-ru\",\"pl-be\",\"pl-ru\",\"pl-uk\",\"pt-en\",\"pt-ru\",\"ro-be\",\"ro-ru\",\"ro-uk\",\"ru-az\",\"ru-be\",\"ru-bg\",\"ru-ca\",\"ru-cs\",\"ru-da\",\"ru-de\",\"ru-el\",\"ru-en\",\"ru-es\",\"ru-et\",\"ru-fi\",\"ru-fr\",\"ru-hr\",\"ru-hu\",\"ru-hy\",\"ru-it\",\"ru-lt\",\"ru-lv\",\"ru-mk\",\"ru-nl\",\"ru-no\",\"ru-pl\",\"ru-pt\",\"ru-ro\",\"ru-sk\",\"ru-sl\",\"ru-sq\",\"ru-sr\",\"ru-sv\",\"ru-tr\",\"ru-uk\",\"sk-en\",\"sk-ru\",\"sl-en\",\"sl-ru\",\"sq-en\",\"sq-ru\",\"sr-be\",\"sr-ru\",\"sr-uk\",\"sv-en\",\"sv-ru\",\"tr-be\",\"tr-de\",\"tr-en\",\"tr-ru\",\"tr-uk\",\"uk-bg\",\"uk-cs\",\"uk-de\",\"uk-en\",\"uk-es\",\"uk-fr\",\"uk-it\",\"uk-pl\",\"uk-ro\",\"uk-ru\",\"uk-sr\",\"uk-tr\"],\"langs\":{\"af\":\"Afrikaans\",\"am\":\"Amharic\",\"ar\":\"Arabic\",\"az\":\"Azerbaijani\",\"ba\":\"Bashkir\",\"be\":\"Belarusian\",\"bg\":\"Bulgarian\",\"bn\":\"Bengali\",\"bs\":\"Bosnian\",\"ca\":\"Catalan\",\"ceb\":\"Cebuano\",\"cs\":\"Czech\",\"cy\":\"Welsh\",\"da\":\"Danish\",\"de\":\"German\",\"el\":\"Greek\",\"en\":\"English\",\"eo\":\"Esperanto\",\"es\":\"Spanish\",\"et\":\"Estonian\",\"eu\":\"Basque\",\"fa\":\"Persian\",\"fi\":\"Finnish\",\"fr\":\"French\",\"ga\":\"Irish\",\"gd\":\"Scottish Gaelic\",\"gl\":\"Galician\",\"gu\":\"Gujarati\",\"he\":\"Hebrew\",\"hi\":\"Hindi\",\"hr\":\"Croatian\",\"ht\":\"Haitian\",\"hu\":\"Hungarian\",\"hy\":\"Armenian\",\"id\":\"Indonesian\",\"is\":\"Icelandic\",\"it\":\"Italian\",\"ja\":\"Japanese\",\"jv\":\"Javanese\",\"ka\":\"Georgian\",\"kk\":\"Kazakh\",\"km\":\"Khmer\",\"kn\":\"Kannada\",\"ko\":\"Korean\",\"ky\":\"Kyrgyz\",\"la\":\"Latin\",\"lb\":\"Luxembourgish\",\"lo\":\"Lao\",\"lt\":\"Lithuanian\",\"lv\":\"Latvian\",\"mg\":\"Malagasy\",\"mhr\":\"Mari\",\"mi\":\"Maori\",\"mk\":\"Macedonian\",\"ml\":\"Malayalam\",\"mn\":\"Mongolian\",\"mr\":\"Marathi\",\"mrj\":\"Hill Mari\",\"ms\":\"Malay\",\"mt\":\"Maltese\",\"my\":\"Burmese\",\"ne\":\"Nepali\",\"nl\":\"Dutch\",\"no\":\"Norwegian\",\"pa\":\"Punjabi\",\"pap\":\"Papiamento\",\"pl\":\"Polish\",\"pt\":\"Portuguese\",\"ro\":\"Romanian\",\"ru\":\"Russian\",\"si\":\"Sinhalese\",\"sk\":\"Slovak\",\"sl\":\"Slovenian\",\"sq\":\"Albanian\",\"sr\":\"Serbian\",\"su\":\"Sundanese\",\"sv\":\"Swedish\",\"sw\":\"Swahili\",\"ta\":\"Tamil\",\"te\":\"Telugu\",\"tg\":\"Tajik\",\"th\":\"Thai\",\"tl\":\"Tagalog\",\"tr\":\"Turkish\",\"tt\":\"Tatar\",\"udm\":\"Udmurt\",\"uk\":\"Ukrainian\",\"ur\":\"Urdu\",\"uz\":\"Uzbek\",\"vi\":\"Vietnamese\",\"xh\":\"Xhosa\",\"yi\":\"Yiddish\",\"zh\":\"Chinese\"}}";
    private static String uk = "{\"dirs\":[\"az-ru\",\"be-bg\",\"be-cs\",\"be-de\",\"be-en\",\"be-es\",\"be-fr\",\"be-it\",\"be-pl\",\"be-ro\",\"be-ru\",\"be-sr\",\"be-tr\",\"bg-be\",\"bg-ru\",\"bg-uk\",\"ca-en\",\"ca-ru\",\"cs-be\",\"cs-en\",\"cs-ru\",\"cs-uk\",\"da-en\",\"da-ru\",\"de-be\",\"de-en\",\"de-es\",\"de-fr\",\"de-it\",\"de-ru\",\"de-tr\",\"de-uk\",\"el-en\",\"el-ru\",\"en-be\",\"en-ca\",\"en-cs\",\"en-da\",\"en-de\",\"en-el\",\"en-es\",\"en-et\",\"en-fi\",\"en-fr\",\"en-hu\",\"en-it\",\"en-lt\",\"en-lv\",\"en-mk\",\"en-nl\",\"en-no\",\"en-pt\",\"en-ru\",\"en-sk\",\"en-sl\",\"en-sq\",\"en-sv\",\"en-tr\",\"en-uk\",\"es-be\",\"es-de\",\"es-en\",\"es-ru\",\"es-uk\",\"et-en\",\"et-ru\",\"fi-en\",\"fi-ru\",\"fr-be\",\"fr-de\",\"fr-en\",\"fr-ru\",\"fr-uk\",\"hr-ru\",\"hu-en\",\"hu-ru\",\"hy-ru\",\"it-be\",\"it-de\",\"it-en\",\"it-ru\",\"it-uk\",\"lt-en\",\"lt-ru\",\"lv-en\",\"lv-ru\",\"mk-en\",\"mk-ru\",\"nl-en\",\"nl-ru\",\"no-en\",\"no-ru\",\"pl-be\",\"pl-ru\",\"pl-uk\",\"pt-en\",\"pt-ru\",\"ro-be\",\"ro-ru\",\"ro-uk\",\"ru-az\",\"ru-be\",\"ru-bg\",\"ru-ca\",\"ru-cs\",\"ru-da\",\"ru-de\",\"ru-el\",\"ru-en\",\"ru-es\",\"ru-et\",\"ru-fi\",\"ru-fr\",\"ru-hr\",\"ru-hu\",\"ru-hy\",\"ru-it\",\"ru-lt\",\"ru-lv\",\"ru-mk\",\"ru-nl\",\"ru-no\",\"ru-pl\",\"ru-pt\",\"ru-ro\",\"ru-sk\",\"ru-sl\",\"ru-sq\",\"ru-sr\",\"ru-sv\",\"ru-tr\",\"ru-uk\",\"sk-en\",\"sk-ru\",\"sl-en\",\"sl-ru\",\"sq-en\",\"sq-ru\",\"sr-be\",\"sr-ru\",\"sr-uk\",\"sv-en\",\"sv-ru\",\"tr-be\",\"tr-de\",\"tr-en\",\"tr-ru\",\"tr-uk\",\"uk-bg\",\"uk-cs\",\"uk-de\",\"uk-en\",\"uk-es\",\"uk-fr\",\"uk-it\",\"uk-pl\",\"uk-ro\",\"uk-ru\",\"uk-sr\",\"uk-tr\"],\"langs\":{\"af\":\"Африкаанс\",\"am\":\"Амхарська\",\"ar\":\"Арабська\",\"az\":\"Азербайджанська\",\"ba\":\"Башкирська\",\"be\":\"Білоруська\",\"bg\":\"Болгарська\",\"bn\":\"Бенгальська\",\"bs\":\"Боснійська\",\"ca\":\"Каталанська\",\"ceb\":\"Себуанська\",\"cs\":\"Чеська\",\"cy\":\"Валлійська\",\"da\":\"Данська\",\"de\":\"Німецька\",\"el\":\"Грецька\",\"en\":\"Англійська\",\"eo\":\"Есперанто\",\"es\":\"Іспанська\",\"et\":\"Естонська\",\"eu\":\"Баскська\",\"fa\":\"Перська\",\"fi\":\"Фінська\",\"fr\":\"Французька\",\"ga\":\"Ірландська\",\"gd\":\"Шотландська (гельська)\",\"gl\":\"Галісійська\",\"gu\":\"Гуджараті\",\"he\":\"Іврит\",\"hi\":\"Хінді\",\"hr\":\"Хорватська\",\"ht\":\"Гаїтянська\",\"hu\":\"Угорська\",\"hy\":\"Вірменська\",\"id\":\"Індонезійська\",\"is\":\"Ісландська\",\"it\":\"Італійська\",\"ja\":\"Японська\",\"jv\":\"Яванська\",\"ka\":\"Грузинська\",\"kk\":\"Казахська\",\"km\":\"Кхмерська\",\"kn\":\"Каннада\",\"ko\":\"Корейська\",\"ky\":\"Киргизька\",\"la\":\"Латина\",\"lb\":\"Люксембурзька\",\"lo\":\"Лаоська\",\"lt\":\"Литовська\",\"lv\":\"Латиська\",\"mg\":\"Малагасійська\",\"mhr\":\"Марійська\",\"mi\":\"Маорі\",\"mk\":\"Македонська\",\"ml\":\"Малаялам\",\"mn\":\"Монгольська\",\"mr\":\"Маратхі\",\"mrj\":\"Гірськомарійська\",\"ms\":\"Малайська\",\"mt\":\"Мальтійська\",\"my\":\"Бірманська\",\"ne\":\"Непальська\",\"nl\":\"Голландська\",\"no\":\"Норвезька\",\"pa\":\"Пенджабська\",\"pap\":\"Пап'яменто\",\"pl\":\"Польська\",\"pt\":\"Португальська\",\"ro\":\"Румунська\",\"ru\":\"Російська\",\"si\":\"Сингальська\",\"sk\":\"Словацька\",\"sl\":\"Словенська\",\"sq\":\"Албанська\",\"sr\":\"Сербська\",\"su\":\"Сунданська\",\"sv\":\"Шведська\",\"sw\":\"Суахілі\",\"ta\":\"Тамільська\",\"te\":\"Телугу\",\"tg\":\"Таджицька\",\"th\":\"Тайська\",\"tl\":\"Тагальська\",\"tr\":\"Турецька\",\"tt\":\"Татарська\",\"udm\":\"Удмуртська\",\"uk\":\"Українська\",\"ur\":\"Урду\",\"uz\":\"Узбецька\",\"vi\":\"В'єтнамська\",\"xh\":\"Коса\",\"yi\":\"Ідиш\",\"zh\":\"Китайська\"}}";
    private static String tr = "{\"dirs\":[\"az-ru\",\"be-bg\",\"be-cs\",\"be-de\",\"be-en\",\"be-es\",\"be-fr\",\"be-it\",\"be-pl\",\"be-ro\",\"be-ru\",\"be-sr\",\"be-tr\",\"bg-be\",\"bg-ru\",\"bg-uk\",\"ca-en\",\"ca-ru\",\"cs-be\",\"cs-en\",\"cs-ru\",\"cs-uk\",\"da-en\",\"da-ru\",\"de-be\",\"de-en\",\"de-es\",\"de-fr\",\"de-it\",\"de-ru\",\"de-tr\",\"de-uk\",\"el-en\",\"el-ru\",\"en-be\",\"en-ca\",\"en-cs\",\"en-da\",\"en-de\",\"en-el\",\"en-es\",\"en-et\",\"en-fi\",\"en-fr\",\"en-hu\",\"en-it\",\"en-lt\",\"en-lv\",\"en-mk\",\"en-nl\",\"en-no\",\"en-pt\",\"en-ru\",\"en-sk\",\"en-sl\",\"en-sq\",\"en-sv\",\"en-tr\",\"en-uk\",\"es-be\",\"es-de\",\"es-en\",\"es-ru\",\"es-uk\",\"et-en\",\"et-ru\",\"fi-en\",\"fi-ru\",\"fr-be\",\"fr-de\",\"fr-en\",\"fr-ru\",\"fr-uk\",\"hr-ru\",\"hu-en\",\"hu-ru\",\"hy-ru\",\"it-be\",\"it-de\",\"it-en\",\"it-ru\",\"it-uk\",\"lt-en\",\"lt-ru\",\"lv-en\",\"lv-ru\",\"mk-en\",\"mk-ru\",\"nl-en\",\"nl-ru\",\"no-en\",\"no-ru\",\"pl-be\",\"pl-ru\",\"pl-uk\",\"pt-en\",\"pt-ru\",\"ro-be\",\"ro-ru\",\"ro-uk\",\"ru-az\",\"ru-be\",\"ru-bg\",\"ru-ca\",\"ru-cs\",\"ru-da\",\"ru-de\",\"ru-el\",\"ru-en\",\"ru-es\",\"ru-et\",\"ru-fi\",\"ru-fr\",\"ru-hr\",\"ru-hu\",\"ru-hy\",\"ru-it\",\"ru-lt\",\"ru-lv\",\"ru-mk\",\"ru-nl\",\"ru-no\",\"ru-pl\",\"ru-pt\",\"ru-ro\",\"ru-sk\",\"ru-sl\",\"ru-sq\",\"ru-sr\",\"ru-sv\",\"ru-tr\",\"ru-uk\",\"sk-en\",\"sk-ru\",\"sl-en\",\"sl-ru\",\"sq-en\",\"sq-ru\",\"sr-be\",\"sr-ru\",\"sr-uk\",\"sv-en\",\"sv-ru\",\"tr-be\",\"tr-de\",\"tr-en\",\"tr-ru\",\"tr-uk\",\"uk-bg\",\"uk-cs\",\"uk-de\",\"uk-en\",\"uk-es\",\"uk-fr\",\"uk-it\",\"uk-pl\",\"uk-ro\",\"uk-ru\",\"uk-sr\",\"uk-tr\"],\"langs\":{\"af\":\"Afrikanca\",\"am\":\"Amharca\",\"ar\":\"Arapça\",\"az\":\"Azerice\",\"ba\":\"Başkurtça\",\"be\":\"Belarusça\",\"bg\":\"Bulgarca\",\"bn\":\"Bengalce\",\"bs\":\"Boşnakça\",\"ca\":\"Katalanca\",\"ceb\":\"Sabuanca\",\"cs\":\"Çekçe\",\"cy\":\"Gal dili\",\"da\":\"Danca\",\"de\":\"Almanca\",\"el\":\"Yunanca\",\"en\":\"İngilizce\",\"eo\":\"Esperanto\",\"es\":\"İspanyolca\",\"et\":\"Estonca\",\"eu\":\"Baskça\",\"fa\":\"Farsça\",\"fi\":\"Fince\",\"fr\":\"Fransızca\",\"ga\":\"İrlandaca\",\"gd\":\"İskoçça (Kelt dili)\",\"gl\":\"Galiçyaca\",\"gu\":\"Gucaratça\",\"he\":\"İbranice\",\"hi\":\"Hintçe\",\"hr\":\"Hırvatça\",\"ht\":\"Haiti dili\",\"hu\":\"Macarca\",\"hy\":\"Ermenice\",\"id\":\"Endonezce\",\"is\":\"İzlandaca\",\"it\":\"İtalyanca\",\"ja\":\"Japonca\",\"jv\":\"Cava dili\",\"ka\":\"Gürcüce\",\"kk\":\"Kazakça\",\"km\":\"Khmerce\",\"kn\":\"Kannada dili\",\"ko\":\"Korece\",\"ky\":\"Kırgızca\",\"la\":\"Latince\",\"lb\":\"Lüksemburgca\",\"lo\":\"Laoca\",\"lt\":\"Litvanca\",\"lv\":\"Letonca\",\"mg\":\"Malgaşça\",\"mhr\":\"Mari dili\",\"mi\":\"Maorice\",\"mk\":\"Makedonca\",\"ml\":\"Malayalamca\",\"mn\":\"Moğolca\",\"mr\":\"Marathi\",\"mrj\":\"Batı Mari dili\",\"ms\":\"Malayca\",\"mt\":\"Maltaca\",\"my\":\"Birmanca\",\"ne\":\"Nepali\",\"nl\":\"Felemenkçe\",\"no\":\"Norveççe\",\"pa\":\"Pencapça\",\"pap\":\"Papiamento\",\"pl\":\"Lehçe\",\"pt\":\"Portekizce\",\"ro\":\"Romence\",\"ru\":\"Rusça\",\"si\":\"Seylanca\",\"sk\":\"Slovakça\",\"sl\":\"Slovence\",\"sq\":\"Arnavutça\",\"sr\":\"Sırpça\",\"su\":\"Sundaca\",\"sv\":\"İsveçce\",\"sw\":\"Svahili\",\"ta\":\"Tamilce\",\"te\":\"Teluguca\",\"tg\":\"Tacikçe\",\"th\":\"Taylandça\",\"tl\":\"Tagalogca\",\"tr\":\"Türkçe\",\"tt\":\"Tatarca\",\"udm\":\"Udmurtça\",\"uk\":\"Ukraynaca\",\"ur\":\"Urduca\",\"uz\":\"Özbekçe\",\"vi\":\"Vietnamca\",\"xh\":\"Xhosa dili\",\"yi\":\"Yidiş\",\"zh\":\"Çince\"}}";
    
    public static final Map<String, String> offlineLanguages = new HashMap<>();

    static {
        offlineLanguages.put(RU, ru);
        offlineLanguages.put(EN, en);
        offlineLanguages.put(UK, uk);
        offlineLanguages.put(TR, tr);
    }

}
