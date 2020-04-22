package br.com.south.batch.sales.utils.message;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageCodeEnum {

    JOB_EXECUTION_START("job.execution.start"),
    JOB_EXECUTION_FINISH("job.execution.finish"),
    INFO_SALES_REPORT_DECODED("info.sales.report.decoded"),
    INFO_SALES_REPORT_RESULT_CREATED("info.sales.report.result.created"),
    FILE_WATCHER("file.watcher"),
    FILE_CREATION_DETECTED("file.creation.detected"),
    FILE_EXISTENT_DETECTED("file.existent.detected"),
    SALES_REPORT_READER("sales.report.reader"),
    ERROR_IO_EXCEPTION("error.io.exception"),
    ERROR_JOB_PARAMETERS_INVALID_EXCEPTION("error.job.parameters.invalid.exception"),
    ERROR_JOB_EXECUTION_RUNNING_EXCEPTION("error.job.execution.running.exception"),
    ERROR_JOB_RESTART_EXCEPTION("error.job.restart.exception"),
    ERROR_JOB_ALREADY_COMPLETE_EXCEPTION("error.job.already.complete.exception"),
    ERROR_INTERRUPTED_EXCEPTION("error.interrupted.exception"),
    ERROR_FACTORY_CLASS("error.factory.class"),
    ERROR_UTILITY_CLASS("error.utility.class"),
    ERROR_DESERIALIZER_CONVERTER("error.deserializer.converter"),
    ERROR_FIELD_FIXED_REQUIRED("error.field.fixed.required"),
    ERROR_FIELD_FIXED_INVALID("error.field.fixed.invalid"),
    ERROR_INVALID_FACTORY_TYPE("error.invalid.factory.type"),
    ERROR_PARAMETER_BASE64_IS_INVALID("error.base64.param.invalid"),
    ERROR_PARAMETER_BASE64_CANNOT_BE_NULL("error.base64.param.cannot.be.null"),
    ERROR_SALES_REPORT_DECODE("error.sales.report.decode"),
    ERROR_FIND_MOST_EXPENSIVE("error.find.most.expensive"),
    ERROR_FIND_WORST_SELLER("error.find.worst.seller"),
    ERROR_SALES_REPORT("error.sales.report"),
    @JsonEnumDefaultValue UNKNOWN("unknown");

    private final String messageKey;

    @Override
    public String toString() {
        return messageKey;
    }
}


