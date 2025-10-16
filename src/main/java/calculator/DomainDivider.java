package calculator;

public class DomainDivider {
    private final String SEPERATOR="\\n";

    public String[] divideDomain(String input){
        int index = input.lastIndexOf(SEPERATOR);
        if(index==-1){
            return new String[]{"",input};
        }
        return new String[]{input.substring(0,index+SEPERATOR.length()),
                input.substring(index+SEPERATOR.length())};
    }
}
