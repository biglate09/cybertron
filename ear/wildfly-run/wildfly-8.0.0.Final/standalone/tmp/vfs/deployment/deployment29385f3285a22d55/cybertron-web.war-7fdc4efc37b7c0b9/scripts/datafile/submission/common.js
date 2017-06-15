/*
 * @author chalermpong.ch@ocean.co.th
 */


(function() {
    APP.namespace('request.parameters', {
        'params.rootDir': '\\ocdata',
        'params.filePattern': '*',
        'params.days': 8,
        'params.dataHome': null,
        'params.branchCode': null,
        'params.writeLog': true
    });
    APP.namespace('query', {
        criteria: $A([
            { 'params.rootDir': "\\ocdata\\ocloan", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]loan$/, /.*[\\\/]loant$/, /.*[\\\/]loan\d{14}$/, /.*[\\\/]loantran$/, /.*[\\\/]xmane$/]) },
            { 'params.rootDir': "\\ocdata\\ocmovemt", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]trmove$/, /.*[\\\/]pdup\d{4}$/, /.*[\\\/]fuup\d{4}$/, /.*[\\\/]tb\d{2}-\d{4}$/]) },
            { 'params.rootDir': "\\ocdata\\ocagency", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]rs\d{8}$/, /.*[\\\/]agentid$/, /.*[\\\/]bagen\d$/, /.*[\\\/]dain\d{2}$/]) },
            { 'params.rootDir': "\\ocdata\\kp\\ocagency", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]kagency$/, /.*[\\\/]kdai\d{2}$/]) },
            { 'params.rootDir': "\\ocdata\\pa", 'params.filePattern': 'p*', 'params.recursive': false, 'validate': $A([/.*[\\\/]pamaster$/, /.*[\\\/]pamasteru$/, /.*[\\\/]pa[am]slipp$/, /.*[\\\/]p[ar]polic[xw]$/, /.*[\\\/]paweblog$/, /.*[\\\/]pareciep$/, /.*[\\\/]parehead$/, /.*[\\\/]padeposi$/, /.*[\\\/]paendosee$/]) },
            { 'params.rootDir': "\\ocdata\\ocmaster", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]db\d{5}$/, /.*[\\\/]addnbs$/, /.*[\\\/]trandb\d{2}$/, /.*[\\\/]tran\d{4}$/]) },
            { 'params.rootDir': "\\ocdata\\ocachier", 'params.filePattern': 'deka????', 'params.recursive': false, 'validate': $A([/.*[\\\/]deka\d{4}$/]) },
            { 'params.rootDir': "\\ocdata\\ocaslips", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]asl[i\d]\d{4}$/, /.*[\\\/]account\d$/, /.*[\\\/]reciepts$/]) },
            { 'params.rootDir': "\\ocdata\\ocheadmn", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]headmn$/, /.*[\\\/]provincer$/, /.*[\\\/]bagen\d$/]) },
            { 'params.rootDir': "\\ocdata\\ocmonth", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]msl\d{4}$/, /.*[\\\/]asli\d{4}$/, /.*[\\\/]p\d{4}$/, /.*[\\\/]r\d{4}$/]) },
            { 'params.rootDir': "\\ocdata\\octax", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]tax\d{2}/]) },
            { 'params.rootDir': "\\ocdata\\ocpslips", 'params.filePattern': 'psl?????', 'params.recursive': false, 'validate': $A([/.*[\\\/]psl\d{5}$/]) },
            { 'params.rootDir': "\\ocdata\\ocmanage", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]bagen\d$/, /.*[\\\/]manage$/, /.*[\\\/]trgua\d{4}$/, /.*[\\\/]dbgua\d{2}$/, /.*[\\\/]kitjagum$/]) },
            { 'params.rootDir': "\\ocdata\\ocmslips", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]account\d$/, /.*[\\\/]maslip\d$/]) },
            { 'params.rootDir': "\\ocdata\\ocnewcas", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]newc\d{4}$/]) },
            { 'params.rootDir': "\\ocdata\\kp\\ocmanage", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]kmanage$/, /.*[\\\/]staffkp$/]) },
            { 'params.rootDir': "\\ocdata\\kp\\kocmonth", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]kasl\d{4}$/, /.*[\\\/]gmsl\d{4}$/, /.*[\\\/]ggr\d{4}$/, /.*[\\\/]gp\d{4}$/]) },
            { 'params.rootDir': "\\ocdata\\kp\\ocaslips", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]kreciepts$/, /.*[\\\/]kas[\dl]\d{4}$/, /.*[\\\/]account2$/, /.*[\\\/]accy$/]) },
            { 'params.rootDir': "\\ocdata\\kp\\ocmslips", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]kmaslip\d$/, /.*[\\\/]account1$/, /.*[\\\/]kmas\d{4}$/]) },
            { 'params.rootDir': "\\ocdata\\kp\\ocnewcas", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]newc\d{4}$/, /.*[\\\/]knew\d{4}$/]) },
            { 'params.rootDir': "\\ocdata\\kp\\ocpslips", 'params.filePattern': 'kps?????', 'params.recursive': false, 'validate': $A([/.*[\\\/]kps\d{5}$/]) },
            { 'params.rootDir': "\\ocdata\\kp\\ocmaster", 'params.filePattern': 'kp?????', 'params.recursive': false, 'validate': $A([/.*[\\\/]kp\d{5}$/]) },
            { 'params.rootDir': "\\ocdata\\kp\\ocmovemt", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]gful\d{4}$/, /.*[\\\/]gpup\d{4}$/, /.*[\\\/]kmovecas$/]) },
            { 'params.rootDir': "\\ocdata\\sm\\ops", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]o[rd]ddata$/, /.*[\\\/]kasl\d{4}$/, /.*[\\\/]bnfdata$/, /.*[\\\/]ordcomm$/]) },
            { 'params.rootDir': "\\ocdata\\sm\\smagency", 'params.filePattern': 'smda??', 'params.recursive': false, 'validate': $A([/.*[\\\/]smda\d{2}$/]) },
            { 'params.rootDir': "\\ocdata\\sm\\smaslips", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]sma\d{5}$/, /.*[\\\/]smreciepts$/]) },
            { 'params.rootDir': "\\ocdata\\sm\\smmaster", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]sm\d{5}$/, /.*[\\\/]smlap\d{4}$/, /.*[\\\/]smed\d{4}$/, /.*[\\\/]gsdata$/, /.*[\\\/]sdata$/]) },
            { 'params.rootDir': "\\ocdata\\sm\\smmovemt", 'params.filePattern': 'sm?????', 'params.recursive': false, 'validate': $A([/.*[\\\/]sm\d{5}$/]) },
            { 'params.rootDir': "\\ocdata\\sm\\smmslips", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]smmaslip$/, /.*[\\\/]smpolicy$/, /.*[\\\/]accountas400$/, /.*[\\\/]accountas400\d{2}$/, /.*[\\\/]delacc2\d{4}$/, /.*[\\\/]smaccount1$/, /.*[\\\/]smaccpoly1$/, /.*[\\\/]accountas400xxxx$/, /.*[\\\/]smmaslipxxx$/]) },
            { 'params.rootDir': "\\ocdata\\sm\\smnewcas", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]smnewc\d{4}$/, /.*[\\\/]smtb.*$/, /.*[\\\/]smnc$/]) },
            { 'params.rootDir': "\\ocdata\\sm\\smpslips", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]smp\d{5}$/, /.*[\\\/]riderpslips$/, /.*[\\\/]pslipx\d{6}$/, /.*[\\\/]smpp$/, /.*[\\\/]smedp$/, /.*[\\\/]riderr$/]) },
            { 'params.rootDir': "\\ocdata\\pa\\pareport", 'params.filePattern': 'uccc*', 'params.recursive': false, 'validate': $A([]) },
            { 'params.rootDir': "\\ocdata\\pa\\pamonth", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]pagw.*$/, /.*[\\\/]patran\d{2}$/, /.*[\\\/]preport[abce]o.*$/]) },
            { 'params.rootDir': "\\ocdata", 'params.filePattern': '*', 'params.recursive': false, 'validate': $A([/.*[\\\/]execlog$/]) }
        ])
    });
    document.on('layout:init', function(event) {
        APP.namespace('grid.files', {
            columns: $A([
                { key: 'fullName', label: 'ไฟล์', sortable: true },
                { key: 'size', label: 'ขนาด (ไบต์)', sortable: true, width: '150px', formatter: APP.formatters.number },
                { key: 'lastWriteTime', label: 'แก้ไขล่าสุด', sortable: true, width: '250px', formatter: APP.formatters.dateTime },
                { key: 'creationTime', label: 'สร้างเมื่อ', sortable: true, width: '250px', formatter: APP.formatters.dateTime },
                { key: 'lastAccessTime', label: 'ใช้งานล่าสุด', sortable: true, width: '250px', formatter: APP.formatters.dateTime }
            ])
        });
    });
})();
