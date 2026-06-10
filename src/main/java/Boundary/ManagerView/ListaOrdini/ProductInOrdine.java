package Boundary.ManagerView.ListaOrdini;

import Boundary.Utils.ImageUtils;
import Boundary.Template.Container.ContainerRow;
import Controller.CatalogoController;
import Entity.Ordini.RigaOrdine;

import javax.swing.*;
import java.awt.*;

public class ProductInOrdine extends ContainerRow {

    public ProductInOrdine(String productId, String qta,String prezzo) {
        super(productId,qta);
        priceLabel.setText("Prezzo: $ "+prezzo);
    }


    @Override
    protected void init(String id){
        CatalogoController catController = new CatalogoController();
        String[] prodotto = catController.caricaProdotto(Long.parseLong(id));

        String nomeProd = prodotto[CatalogoController.NOME];
        String imgPath  = prodotto[CatalogoController.IMG_PATH];

        imgLabel.setIcon(ImageUtils.getIconScaled(imgPath, 100));
        nomeLabel.setText(nomeProd);
    }
}
