package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.HistoriaClinicaDTO;
import com.utn.tesis.model.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 15/05/16
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */
public class HistoriaClinicaMapper {
    @Inject
    DetalleMapper detalleMapper;

    public HistoriaClinicaDTO toDTO(HistoriaClinica historiaClinica){
        if ( historiaClinica == null ) {
            return null;
        }

        HistoriaClinicaDTO historiaClinicaDTO = new HistoriaClinicaDTO();

        historiaClinicaDTO.setId( historiaClinica.getId() );
        if ( historiaClinica.getVersion() != null ) {
            historiaClinicaDTO.setVersion( historiaClinica.getVersion() );
        }
        historiaClinicaDTO.setFechaApertura(historiaClinica.getFechaApertura());

        historiaClinicaDTO.setG1p1(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(0)));
        historiaClinicaDTO.setG1p2(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(1)));
        historiaClinicaDTO.setG1p3(detalleMapper.campoFechaToDTO((CampoFecha) historiaClinica.getDetallesHC().get(2)));

        historiaClinicaDTO.setG2p1(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(3)));
        historiaClinicaDTO.setG2p2(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(4)));
        historiaClinicaDTO.setG2p3(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(5)));
        historiaClinicaDTO.setG2p4(detalleMapper.campoFechaToDTO((CampoFecha) historiaClinica.getDetallesHC().get(6)));

        historiaClinicaDTO.setG3p1(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(7))) ;
        historiaClinicaDTO.setG3p2(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(8))) ;
        historiaClinicaDTO.setG3p3(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(9))) ;
        historiaClinicaDTO.setG3p4(detalleMapper.campoFechaToDTO((CampoFecha) historiaClinica.getDetallesHC().get(10)));
        historiaClinicaDTO.setG3p5(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(11)));
        historiaClinicaDTO.setG3p6(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(12)));
        historiaClinicaDTO.setG3p7(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(13)));
        historiaClinicaDTO.setG3p8(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(14)));
        historiaClinicaDTO.setG3p9(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(15)));
        historiaClinicaDTO.setG3p10(detalleMapper.campoFechaToDTO((CampoFecha) historiaClinica.getDetallesHC().get(16)));
        historiaClinicaDTO.setG3p11(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(17)));

        historiaClinicaDTO.setG4p1(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(18)));
        historiaClinicaDTO.setG4p2(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(19)));
        historiaClinicaDTO.setG4p3(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(20)));
        historiaClinicaDTO.setG4p4(detalleMapper.campoFechaToDTO((CampoFecha) historiaClinica.getDetallesHC().get(21)));

        historiaClinicaDTO.setG5p1(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(22)));
        historiaClinicaDTO.setG5p2(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(23)));
        historiaClinicaDTO.setG5p3(detalleMapper.campoFechaToDTO((CampoFecha) historiaClinica.getDetallesHC().get(24)));
        historiaClinicaDTO.setG5p4(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(25)));
        historiaClinicaDTO.setG5p5(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(26)));
        historiaClinicaDTO.setG5p6(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(27)));
        historiaClinicaDTO.setG5p7(detalleMapper.campoFechaToDTO((CampoFecha) historiaClinica.getDetallesHC().get(28)));
        historiaClinicaDTO.setG5p8(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(29)));
        historiaClinicaDTO.setG5p9(detalleMapper.campoFechaToDTO((CampoFecha) historiaClinica.getDetallesHC().get(30)));

        historiaClinicaDTO.setG6p1(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(31)));
        historiaClinicaDTO.setG6p2(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(32)));
        historiaClinicaDTO.setG6p3(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(33)));
        historiaClinicaDTO.setG6p4(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(34)));

        historiaClinicaDTO.setG7p1(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(35)));
        historiaClinicaDTO.setG7p2(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(36)));

        historiaClinicaDTO.setG8p1(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(37)));
        historiaClinicaDTO.setG8p2(detalleMapper.campoEnumerableToDTO((CampoEnumerable) historiaClinica.getDetallesHC().get(38)));
        historiaClinicaDTO.setG8p3(detalleMapper.campoEnumerableToDTO((CampoEnumerable) historiaClinica.getDetallesHC().get(39)));
        historiaClinicaDTO.setG8p4(detalleMapper.campoEnumerableToDTO((CampoEnumerable) historiaClinica.getDetallesHC().get(40)));
        historiaClinicaDTO.setG8p5(detalleMapper.campoEnumerableToDTO((CampoEnumerable) historiaClinica.getDetallesHC().get(41)));
        historiaClinicaDTO.setG8p6(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(42)));

        historiaClinicaDTO.setG9p1(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(43)));
        historiaClinicaDTO.setG9p2(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(44)));
        historiaClinicaDTO.setG9p3(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(45)));
        historiaClinicaDTO.setG9p4(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(46)));

        historiaClinicaDTO.setG10p1(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(47)));
        historiaClinicaDTO.setG10p2(detalleMapper.campoDetalleToDTO((CampoDetalle)historiaClinica.getDetallesHC().get(48)));
        historiaClinicaDTO.setG10p3(detalleMapper.campoFechaToDTO((CampoFecha)historiaClinica.getDetallesHC().get(49)));
        historiaClinicaDTO.setG10p4(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(50)));
        historiaClinicaDTO.setG10p5(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(51)));
        historiaClinicaDTO.setG10p6(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(52)));
        historiaClinicaDTO.setG10p7(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(53)));
        historiaClinicaDTO.setG10p8(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(54)));
        historiaClinicaDTO.setG10p9(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(55)));

        historiaClinicaDTO.setG11p1(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(56)));
        historiaClinicaDTO.setG11p2(detalleMapper.campoEnumerableToDTO((CampoEnumerable) historiaClinica.getDetallesHC().get(57)));
        historiaClinicaDTO.setG11p3(detalleMapper.campoEnumerableToDTO((CampoEnumerable) historiaClinica.getDetallesHC().get(58)));
        historiaClinicaDTO.setG11p4(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(59)));
        historiaClinicaDTO.setG11p5(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(60)));
        historiaClinicaDTO.setG11p6(detalleMapper.campoFechaToDTO((CampoFecha) historiaClinica.getDetallesHC().get(61)));

        historiaClinicaDTO.setG12p1(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(62)));
        historiaClinicaDTO.setG12p2(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(63)));

        historiaClinicaDTO.setG13p1(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(64)));
        historiaClinicaDTO.setG13p2(detalleMapper.campoFechaToDTO((CampoFecha) historiaClinica.getDetallesHC().get(65)));
        historiaClinicaDTO.setG13p3(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(66)));
        historiaClinicaDTO.setG13p4(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(67)));

        historiaClinicaDTO.setG14p1(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(68)));
        historiaClinicaDTO.setG14p2(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(69)));
        historiaClinicaDTO.setG14p3(detalleMapper.campoFechaToDTO((CampoFecha) historiaClinica.getDetallesHC().get(70)));
        historiaClinicaDTO.setG14p4(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(71)));

        historiaClinicaDTO.setG15p1(detalleMapper.campoSiNoToDTO((CampoSiNo) historiaClinica.getDetallesHC().get(72)));
        historiaClinicaDTO.setG15p2(detalleMapper.campoDetalleToDTO((CampoDetalle) historiaClinica.getDetallesHC().get(73)));

        historiaClinicaDTO.setG15p3(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(74)));
        historiaClinicaDTO.setG15p4(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(75)));
        historiaClinicaDTO.setG15p5(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(76)));
        historiaClinicaDTO.setG15p6(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(77)));
        historiaClinicaDTO.setG15p7(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(78)));
        historiaClinicaDTO.setG15p8(detalleMapper.campoFechaToDTO((CampoFecha)	historiaClinica.getDetallesHC().get(79)));
        historiaClinicaDTO.setG15p9(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(80)));
        historiaClinicaDTO.setG15p10(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(81)));
        historiaClinicaDTO.setG15p11(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(82)));
        historiaClinicaDTO.setG15p12(detalleMapper.campoFechaToDTO((CampoFecha)	historiaClinica.getDetallesHC().get(83)));
        historiaClinicaDTO.setG15p13(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(84)));
        historiaClinicaDTO.setG16p1(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(85)));
        historiaClinicaDTO.setG16p2(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(86)));
        historiaClinicaDTO.setG16p3(detalleMapper.campoFechaToDTO((CampoFecha)	historiaClinica.getDetallesHC().get(87)));
        historiaClinicaDTO.setG17p1(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(88)));
        historiaClinicaDTO.setG17p2(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(89)));
        historiaClinicaDTO.setG17p3(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(90)));
        historiaClinicaDTO.setG17p4(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(91)));
        historiaClinicaDTO.setG17p5(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(92)));
        historiaClinicaDTO.setG17p6(detalleMapper.campoFechaToDTO((CampoFecha)	historiaClinica.getDetallesHC().get(93)));
        historiaClinicaDTO.setG17p7(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(94)));

        historiaClinicaDTO.setG18p1(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(95)));
        historiaClinicaDTO.setG18p2(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(96)));
        historiaClinicaDTO.setG18p3(detalleMapper.campoEnumerableToDTO((CampoEnumerable)	historiaClinica.getDetallesHC().get(97)));
        historiaClinicaDTO.setG18p4(detalleMapper.campoEnumerableToDTO((CampoEnumerable)	historiaClinica.getDetallesHC().get(98)));
        historiaClinicaDTO.setG18p5(detalleMapper.campoEnumerableToDTO((CampoEnumerable)	historiaClinica.getDetallesHC().get(99)));
        historiaClinicaDTO.setG19p1(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(100)));
        historiaClinicaDTO.setG19p2(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(101)));
        historiaClinicaDTO.setG19p3(detalleMapper.campoFechaToDTO((CampoFecha)	historiaClinica.getDetallesHC().get(102)));
        historiaClinicaDTO.setG19p4(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(103)));
        historiaClinicaDTO.setG19p5(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(104)));
        historiaClinicaDTO.setG19p6(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(105)));
        historiaClinicaDTO.setG19p7(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(106)));
        historiaClinicaDTO.setG19p8(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(107)));
        historiaClinicaDTO.setG19p9(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(108)));
        historiaClinicaDTO.setG20p1(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(109)));
        historiaClinicaDTO.setG20p2(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(110)));
        historiaClinicaDTO.setG20p3(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(111)));
        historiaClinicaDTO.setG20p4(detalleMapper.campoFechaToDTO((CampoFecha)	historiaClinica.getDetallesHC().get(112)));
        historiaClinicaDTO.setG20p5(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(113)));
        historiaClinicaDTO.setG20p6(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(114)));
        historiaClinicaDTO.setG20p7(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(115)));
        historiaClinicaDTO.setG20p8(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(116)));
        historiaClinicaDTO.setG20p9(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(117)));
        historiaClinicaDTO.setG20p10(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(118)));
        historiaClinicaDTO.setG20p11(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(119)));
        historiaClinicaDTO.setG20p12(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(120)));
        historiaClinicaDTO.setG20p13(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(121)));
        historiaClinicaDTO.setG20p14(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(122)));
        historiaClinicaDTO.setG21p1(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(123)));
        historiaClinicaDTO.setG21p2(detalleMapper.campoFechaToDTO((CampoFecha)	historiaClinica.getDetallesHC().get(124)));
        historiaClinicaDTO.setG21p3(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(125)));
        historiaClinicaDTO.setG21p4(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(126)));
        historiaClinicaDTO.setG21p5(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(127)));
        historiaClinicaDTO.setG21p6(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(128)));
        historiaClinicaDTO.setG21p7(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(129)));
        historiaClinicaDTO.setG21p8(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(130)));
        historiaClinicaDTO.setG21p9(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(131)));
        historiaClinicaDTO.setG21p10(detalleMapper.campoFechaToDTO((CampoFecha)	historiaClinica.getDetallesHC().get(132)));
        historiaClinicaDTO.setG21p11(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(133)));
        historiaClinicaDTO.setG21p12(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(134)));
        historiaClinicaDTO.setG21p13(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(135)));
        historiaClinicaDTO.setG22p1(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(136)));
        historiaClinicaDTO.setG22p2(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(137)));
        historiaClinicaDTO.setG22p3(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(138)));
        historiaClinicaDTO.setG22p4(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(139)));
        historiaClinicaDTO.setG22p5(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(140)));
        historiaClinicaDTO.setG23p1(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(141)));
        historiaClinicaDTO.setG23p2(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(142)));
        historiaClinicaDTO.setG24p1(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(143)));
        historiaClinicaDTO.setG24p2(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(144)));
        historiaClinicaDTO.setG25p1(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(145)));
        historiaClinicaDTO.setG25p2(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(146)));
        historiaClinicaDTO.setG25p3(detalleMapper.campoEnumerableToDTO((CampoEnumerable)	historiaClinica.getDetallesHC().get(147)));
        historiaClinicaDTO.setG25p4(detalleMapper.campoEnumerableToDTO((CampoEnumerable)	historiaClinica.getDetallesHC().get(148)));
        historiaClinicaDTO.setG25p5(detalleMapper.campoEnumerableToDTO((CampoEnumerable)	historiaClinica.getDetallesHC().get(149)));
        historiaClinicaDTO.setG26p1(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(150)));
        historiaClinicaDTO.setG26p2(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(151)));
        historiaClinicaDTO.setG27p1(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(152)));
        historiaClinicaDTO.setG27p2(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(153)));
        historiaClinicaDTO.setG27p3(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(154)));
        historiaClinicaDTO.setG27p4(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(155)));
        historiaClinicaDTO.setG28p1(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(156)));
        historiaClinicaDTO.setG28p2(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(157)));
        historiaClinicaDTO.setG28p3(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(158)));
        historiaClinicaDTO.setG28p4(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(159)));
        historiaClinicaDTO.setG28p5(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(160)));
        historiaClinicaDTO.setG28p6(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(161)));
        historiaClinicaDTO.setG29p1(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(162)));
        historiaClinicaDTO.setG29p2(detalleMapper.campoSiNoToDTO((CampoSiNo)	historiaClinica.getDetallesHC().get(163)));
        historiaClinicaDTO.setG29p3(detalleMapper.campoEnumerableToDTO((CampoEnumerable)	historiaClinica.getDetallesHC().get(164)));
        historiaClinicaDTO.setG29p4(detalleMapper.campoEnumerableToDTO((CampoEnumerable)	historiaClinica.getDetallesHC().get(165)));
        historiaClinicaDTO.setG29p5(detalleMapper.campoDetalleToDTO((CampoDetalle)	historiaClinica.getDetallesHC().get(166)));
        historiaClinicaDTO.setOdontograma(historiaClinica.getOdontograma());

        return historiaClinicaDTO;
    }

    public List<HistoriaClinicaDTO> toDTOList(List<HistoriaClinica> list){
        if(list != null && list.size() <= 0){
            return null;
        }
        List<HistoriaClinicaDTO> dtoList = new ArrayList<HistoriaClinicaDTO>();

        for (HistoriaClinica historiaClinica : list) {
            HistoriaClinicaDTO dto = toDTO(historiaClinica);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public HistoriaClinica fromDTO(HistoriaClinicaDTO historiaClinicaDTO) {
        if ( historiaClinicaDTO == null ) {
            return null;
        }

        HistoriaClinica historiaClinica = new HistoriaClinica();

        historiaClinica.setVersion( historiaClinicaDTO.getVersion() );
        historiaClinica.setId( historiaClinicaDTO.getId() );
        historiaClinica.setFechaApertura( historiaClinicaDTO.getFechaApertura() );

        List<DetalleHistoriaClinica> detallesHC = new ArrayList<DetalleHistoriaClinica>();

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG1p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG1p2()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG1p3()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG2p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG2p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG2p3()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG2p4()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG3p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG3p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG3p3()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG3p4()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG3p5()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG3p6()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG3p7()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG3p8()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG3p9()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG3p10()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG3p11()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG4p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG4p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG4p3()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG4p4()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG5p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG5p2()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG5p3()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG5p4()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG5p5()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG5p6()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG5p7()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG5p8()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG5p9()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG6p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG6p2()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG6p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG6p4()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG7p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG7p2()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG8p1()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG8p2()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG8p3()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG8p4()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG8p5()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG8p6()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG9p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG9p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG9p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG9p4()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG10p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG10p2()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG10p3()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG10p4()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG10p5()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG10p6()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG10p7()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG10p8()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG10p9()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG11p1()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG11p2()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG11p3()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG11p4()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG11p5()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG11p6()));
        
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG12p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG12p2()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG13p1()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG13p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG13p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG13p4()));
        
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG14p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG14p2()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG14p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG14p4()));
        
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG15p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG15p2()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG15p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG15p4()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG15p5()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG15p6()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG15p7()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG15p8()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG15p9()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG15p10()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG15p11()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG15p12()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG15p13()));
        
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG16p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG16p2()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG16p3()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG17p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG17p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG17p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG17p4()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG17p5()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG17p6()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG17p7()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG18p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG18p2()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG18p3()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG18p4()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG18p5()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG19p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG19p2()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG19p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG19p4()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG19p5()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG19p6()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG19p7()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG19p8()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG19p9()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG20p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG20p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG20p3()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG20p4()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG20p5()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG20p6()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG20p7()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG20p8()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG20p9()));
        
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG20p10()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG20p11()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG20p12()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG20p13()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG20p14()));
        
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG21p1()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG21p2()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG21p3()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG21p4()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG21p5()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG21p6()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG21p7()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG21p8()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG21p9()));
        
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG21p10()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG21p11()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG21p12()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG21p13()));
       
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG22p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG22p2()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG22p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG22p4()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG22p5()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG23p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG23p2()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG24p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG24p2()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG25p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG25p2()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG25p3()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG25p4()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG25p5()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG26p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG26p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG27p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG27p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG27p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG27p4()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG28p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG28p2()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG28p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG28p4()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG28p5()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG28p6()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG29p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG29p2()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG29p3()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG29p4()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG29p5()));

        historiaClinica.setDetallesHC(detallesHC);

        return historiaClinica;
    }

    public HistoriaClinica updateHistoriaClinicaFromDTO(HistoriaClinicaDTO historiaClinicaDTO, HistoriaClinica historiaClinica) {
        if ( historiaClinicaDTO == null ) {
            return null;
        }

        historiaClinica.setVersion( historiaClinicaDTO.getVersion() );
        historiaClinica.setId( historiaClinicaDTO.getId() );
        historiaClinica.setFechaApertura( historiaClinicaDTO.getFechaApertura() );

        if ( historiaClinica.getDetallesHC() != null ) {
            historiaClinica.getDetallesHC().clear();
            historiaClinica.getDetallesHC().addAll(getDetallesFromDTO(historiaClinicaDTO));
        }
        else {
            historiaClinica.getDetallesHC().addAll(getDetallesFromDTO(historiaClinicaDTO));
        }

        return historiaClinica;
    }

    private List<DetalleHistoriaClinica> getDetallesFromDTO(HistoriaClinicaDTO historiaClinicaDTO){
        List<DetalleHistoriaClinica> detallesHC = new ArrayList<DetalleHistoriaClinica>();

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG1p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG1p2()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG1p3()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG2p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG2p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG2p3()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG2p4()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG3p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG3p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG3p3()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG3p4()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG3p5()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG3p6()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG3p7()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG3p8()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG3p9()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG3p10()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG3p11()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG4p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG4p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG4p3()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG4p4()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG5p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG5p2()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG5p3()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG5p4()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG5p5()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG5p6()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG5p7()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG5p8()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG5p9()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG6p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG6p2()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG6p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG6p4()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG7p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG7p2()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG8p1()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG8p2()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG8p3()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG8p4()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG8p5()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG8p6()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG9p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG9p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG9p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG9p4()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG10p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG10p2()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG10p3()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG10p4()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG10p5()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG10p6()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG10p7()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG10p8()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG10p9()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG11p1()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG11p2()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG11p3()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG11p4()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG11p5()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG11p6()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG12p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG12p2()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG13p1()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG13p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG13p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG13p4()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG14p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG14p2()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG14p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG14p4()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG15p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG15p2()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG15p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG15p4()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG15p5()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG15p6()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG15p7()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG15p8()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG15p9()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG15p10()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG15p11()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG15p12()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG15p13()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG16p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG16p2()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG16p3()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG17p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG17p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG17p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG17p4()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG17p5()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG17p6()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG17p7()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG18p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG18p2()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG18p3()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG18p4()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG18p5()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG19p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG19p2()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG19p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG19p4()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG19p5()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG19p6()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG19p7()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG19p8()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG19p9()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG20p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG20p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG20p3()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG20p4()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG20p5()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG20p6()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG20p7()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG20p8()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG20p9()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG20p10()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG20p11()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG20p12()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG20p13()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG20p14()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG21p1()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG21p2()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG21p3()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG21p4()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG21p5()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG21p6()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG21p7()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG21p8()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG21p9()));
        detallesHC.add(detalleMapper.campoFechaFromDTO(historiaClinicaDTO.getG21p10()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG21p11()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG21p12()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG21p13()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG22p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG22p2()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG22p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG22p4()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG22p5()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG23p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG23p2()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG24p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG24p2()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG25p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG25p2()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG25p3()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG25p4()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG25p5()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG26p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG26p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG27p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG27p2()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG27p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG27p4()));

        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG28p1()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG28p2()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG28p3()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG28p4()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG28p5()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG28p6()));

        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG29p1()));
        detallesHC.add(detalleMapper.campoSiNoFromDTO(historiaClinicaDTO.getG29p2()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG29p3()));
        detallesHC.add(detalleMapper.campoEnumerableFromDTO(historiaClinicaDTO.getG29p4()));
        detallesHC.add(detalleMapper.campoDetalleFromDTO(historiaClinicaDTO.getG29p5()));

        return detallesHC;
    }

}
