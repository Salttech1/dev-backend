package kraheja.commons.filter;



public final class GenericAuditContextHolder {

    private static final ThreadLocal<GenericAuditDto> GENERIC_HEADER_PARAMETER_THREAD_LOCAL = new ThreadLocal<>();

    private GenericAuditContextHolder() {
    }

    public static void setContext(GenericAuditDto genericRestParamDto) {
        GENERIC_HEADER_PARAMETER_THREAD_LOCAL.set(genericRestParamDto);
    }

    public static GenericAuditDto getContext() {
        return GENERIC_HEADER_PARAMETER_THREAD_LOCAL.get();
    }

    public static void clearContext() {
        GENERIC_HEADER_PARAMETER_THREAD_LOCAL.remove();
    }
}
