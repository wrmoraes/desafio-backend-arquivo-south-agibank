package br.com.south.batch.sales.service;

import br.com.south.batch.sales.converter.ReportConverter;
import br.com.south.batch.sales.exceptions.FindMostExpensiveException;
import br.com.south.batch.sales.exceptions.FindWorstSellerException;
import br.com.south.batch.sales.exceptions.SalesReportException;
import br.com.south.batch.sales.model.dto.SalesReportInputDTO;
import br.com.south.batch.sales.model.dto.SalesReportInputSaleDTO;
import br.com.south.batch.sales.model.dto.SalesReportResultDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static br.com.south.batch.sales.utils.message.MessageCodeEnum.ERROR_FIND_MOST_EXPENSIVE;
import static br.com.south.batch.sales.utils.message.MessageCodeEnum.ERROR_FIND_WORST_SELLER;
import static br.com.south.batch.sales.utils.message.MessageUtils.getMessage;

@Service
public class SalesReportService {

    public SalesReportInputDTO convertBase64ToSalesReportDTO(String base64) throws SalesReportException {
        return ReportConverter.getSalesReportConverter().convertToSalesReport(base64);
    }

    public SalesReportResultDTO processSalesReport(SalesReportInputDTO salesReportInput) throws FindMostExpensiveException, FindWorstSellerException {

        BigDecimal sellerCount = BigDecimal.valueOf(salesReportInput.getSellers().size());
        BigDecimal customersCount = BigDecimal.valueOf(salesReportInput.getCustomers().size());
        BigDecimal mostExpensiveSale = getMostExpensiveSale(salesReportInput.getSales());
        String worstSeller = getWorstSeller(salesReportInput.getSales());

        return getResults(customersCount, sellerCount, mostExpensiveSale, worstSeller);
    }

    private SalesReportResultDTO getResults(BigDecimal customersNum, BigDecimal sellersNum,
                                            BigDecimal mostExpensiveSale, String worstSeller){
        return SalesReportResultDTO.builder()
                .customersNum(customersNum)
                .sellersNum(sellersNum)
                .mostExpensiveSale(mostExpensiveSale)
                .worstSeller(worstSeller)
                .build();
    }

    private BigDecimal getMostExpensiveSale(List<SalesReportInputSaleDTO> sales) throws FindMostExpensiveException {
        return sales.stream()
                .collect(
                        Collectors.groupingBy(
                                SalesReportInputSaleDTO::getId,
                                Collectors.summingDouble(SalesReportInputSaleDTO::getTotalSale)
                        ))
                .entrySet()
                .stream()
                .max(Comparator.comparingDouble(entry-> entry.getValue()))
                .orElseThrow(()-> new FindMostExpensiveException(getMessage(ERROR_FIND_MOST_EXPENSIVE))).getKey();
    }

    private String getWorstSeller(List<SalesReportInputSaleDTO> sales) throws FindWorstSellerException {
        return sales.stream()
                .collect(
                        Collectors.groupingBy(
                                SalesReportInputSaleDTO::getSeller,
                                Collectors.summingDouble(SalesReportInputSaleDTO::getTotalSale)
                        ))
                .entrySet()
                .stream()
                .min(Comparator.comparingDouble(entry-> entry.getValue()))
                .orElseThrow(()-> new FindWorstSellerException(getMessage(ERROR_FIND_WORST_SELLER))).getKey();
    }
}
