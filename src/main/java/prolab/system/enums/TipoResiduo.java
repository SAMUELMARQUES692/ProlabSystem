package prolab.system.enums;

public enum TipoResiduo {

    CODIGO_16_05_08("160508", "Produtos químicos orgânicos fora de uso contendo ou compostos por substâncias perigosas"),
    CODIGO_15_02_02("150202", "Absorventes, materiais filtrantes (incluindo filtros de óleo não anteriormente especificados), panos de limpeza e vestuário de proteção, contaminados por substâncias perigosas"),
    CODIGO_15_01_10("150110", "Embalagens de qualquer um dos tipos acima descritos contendo ou contaminadas por resíduos de substâncias perigosas"),
    CODIGO_20_01_35("200135", "Produtos eletroeletrônicos e seus componentes fora de uso não abrangido em 20 01 21 ou 20 01 23 contendo componentes perigosos"),
    CODIGO_07_05_13("070513", "Resíduos sólidos contendo substâncias perigosas");

    private final String codigo;
    private final String descricao;

    TipoResiduo(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

}
