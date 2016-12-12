<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPages/CST.Master" AutoEventWireup="true" CodeBehind="PreShip.aspx.cs" Inherits="HP.TS.SSDO.CST.Web.Order.PreShip" %>

<%@ Register Src="~/WebControls/PagerForGridView.ascx" TagName="PagerForGridView" TagPrefix="ucCommon" %>
<%@ Register Assembly="HP.TS.SSDO.CST.Web" Namespace="HP.TS.SSDO.CST.Web.WebControls" TagPrefix="cc" %>
<%@ Register Src="~/WebControls/ConsumablePartControl.ascx" TagName="ConsumablePartControl" TagPrefix="uc" %>
<%@ Register Assembly="HP.TS.SSDO.CST.Web" Namespace="HP.TS.SSDO.CST.Web.WebControls" TagPrefix="cc" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <asp:ScriptManager runat="server" ID="scritpManager" AsyncPostBackTimeout="6000"></asp:ScriptManager>
    <script type="text/javascript" src="/Scripts/My97DatePicker/WdatePicker.js"></script>
    <asp:UpdatePanel ID="uptPnl" runat="server" UpdateMode="Always" ChildrenAsTriggers="true">
        <ContentTemplate>
            <h1>Kitting</h1>
            <asp:Panel runat="server" ID="pnlSearch" DefaultButton="btnSearch" CssClass="marginbotton5">
                <h3>Search Filter(s)</h3>
                <div class="borderpanel">
                    <table class="contenttable">
                        <tr>
                            <td class="contentpanel">
                                <fieldset>
                                    <legend>Project Code</legend>
                                    <asp:TextBox runat="server" ID="txtProjectCode" CssClass="largetextbox" MaxLength="4"></asp:TextBox>
                                </fieldset>
                            </td>
                            <td class="contentpanel">
                                <fieldset>
                                    <legend>Order Number</legend>
                                    <asp:TextBox runat="server" ID="txtOrderNumber" CssClass="largetextbox valid-number"></asp:TextBox>
                                    <asp:RegularExpressionValidator ID="revlOrderNumber" runat="server" ControlToValidate="txtOrderNumber"
                                        Display="Dynamic" ValidationExpression="^[1-9]\d*" ValidationGroup="ValidateInput"
                                        CssClass="error" ErrorMessage="<%# HP.TS.SSDO.CST.Library.Constants.ConstOnlyPositiveInteger%>" EnableClientScript="true" ToolTip="Required" SetFocusOnError="true"></asp:RegularExpressionValidator>
                                </fieldset>
                            </td>
                        </tr>
                        <tr>
                            <td class="contentpanel">
                                <fieldset>
                                    <legend>Required Ship Date From</legend>
                                    <input class="Wdate largetextbox" type="text" id="txtDateFrom" runat="server" onfocus="WdatePicker({isShowClear:true,skin:'twoer',dateFmt:'MM-dd-yyyy',lang:'en'})" />
                                </fieldset>
                            </td>
                            <td class="contentpanel">
                                <fieldset>
                                    <legend>Required Ship Date To</legend>
                                    <input class="Wdate largetextbox" type="text" id="txtDateTo" runat="server" onfocus="WdatePicker({isShowClear:true,skin:'twoer',dateFmt:'MM-dd-yyyy',lang:'en'})" />
                                </fieldset>
                            </td>
                        </tr>
                        <tr>
                            <td class="contentpanel">
                                <fieldset>
                                    <legend>Status</legend>
                                    <asp:DropDownList runat="server" ID="ddlStatus" CssClass="largetextbox">
                                    </asp:DropDownList>
                                </fieldset>
                            </td>
                            <td class="contentpanel">
                                <fieldset>
                                    <legend>Box ID</legend>
                                    <asp:TextBox runat="server" ID="txtBoxID" CssClass="largetextbox"></asp:TextBox>
                                    <asp:RegularExpressionValidator ID="revBoxID" runat="server" ControlToValidate="txtBoxID"
                                        Display="Dynamic" ValidationExpression="^[1-9]\d*" ValidationGroup="ValidateInput"
                                        CssClass="error" ErrorMessage="<%# HP.TS.SSDO.CST.Library.Constants.ConstOnlyPositiveInteger%>" EnableClientScript="true" ToolTip="Required" SetFocusOnError="true"></asp:RegularExpressionValidator>
                                </fieldset>
                            </td>
                        </tr>
                        <tr>
                            <td class="contentpanel">
                                <fieldset>
                                    <legend>Quote Number</legend>
                                    <asp:TextBox runat="server" ID="txtQuoteNumber" CssClass="largetextbox valid-number"></asp:TextBox>
                                    <asp:RegularExpressionValidator ID="revlQuoteNumber" runat="server" ControlToValidate="txtQuoteNumber"
                                        Display="Dynamic" ValidationExpression="^[1-9]\d*" ValidationGroup="ValidateInput"
                                        CssClass="error" ErrorMessage="<%# HP.TS.SSDO.CST.Library.Constants.ConstOnlyPositiveInteger%>" EnableClientScript="true" ToolTip="Required" SetFocusOnError="true"></asp:RegularExpressionValidator>
                                </fieldset>
                            </td>
                            <td class="contentpanel"></td>
                        </tr>
                        <tr>
                            <td class="contentpanel">
                                <div class="btnDiv">
                                    <asp:Button runat="server" ID="btnSearch" CssClass="mediumbutton btnposition" ValidationGroup="ValidateInput" Text="Search" OnClick="btnSearch_Click" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <cc:ConfirmDialog ID="confirmForKITWIPChild" runat="server" Message="This order has child order in KITWIP status, do you wish to combine?"
                    OnResponse="confirmForKITWIPChild_Response" Visible="false" ConfirmDialogType="Custom" CssClass="borderpanel"
                    ButtonCssClass="mediumbutton" Style="width: 50%" />
            </asp:Panel>
            <asp:Panel runat="server" ID="pnlResult" Visible="false" CssClass="borderpanel marginbotton5" DefaultButton="btnNoAction">
                <h3>Kitting Lookup</h3>
                <table class="grid" cellspacing="0" rules="all" border="1" style="border-collapse: collapse;">
                    <tr>
                        <th scope="col" style="width: 140px;"></th>
                        <th scope="col">Project Code</th>
                        <th scope="col">Order Number</th>
                        <th scope="col">Status</th>
                        <th scope="col" runat="server" id="thCustRef1">
                            <asp:Label runat="server" ID="lblCustRef1" Text="CustomerReference1"></asp:Label></th>
                        <th scope="col" runat="server" id="thCustRef2">
                            <asp:Label runat="server" ID="lblCustRef2" Text="CustomerReference2"></asp:Label></th>
                        <th scope="col" runat="server" id="thCustRef3">
                            <asp:Label runat="server" ID="lblCustRef3" Text="CustomerReference3"></asp:Label></th>
                        <th scope="col" runat="server" id="thCustRef4">
                            <asp:Label runat="server" ID="lblCustRef4" Text="CustomerReference4"></asp:Label></th>
                        <th scope="col">Required Ship Date</th>
                    </tr>
                    <asp:Repeater ID="rptOrder" runat="server" OnItemDataBound="rptOrder_ItemDataBound">
                        <ItemTemplate>
                            <tr class="TableRow" runat="server" id="trOrderLine">
                                <td>
                                    <asp:LinkButton ID="lbtnVerify" runat="server" OnClick="lbtnVerify_Click" Text='Verify/Edit'
                                        CommandArgument='<%# Eval("PK_ORD_Order") %>' ClientIDMode="AutoID"></asp:LinkButton></td>
                                <td>
                                    <%# Eval("ProjectCode")%>
                                </td>
                                <td>
                                    <%# Eval("PK_ORD_Order")%></td>
                                <td>
                                    <%# Eval("OrderStatus")%></td>
                                <td runat="server" id="tdCustRef1">
                                    <%# Eval("CustomerReference1")%></td>
                                <td runat="server" id="tdCustRef2">
                                    <%# Eval("CustomerReference2")%></td>
                                <td runat="server" id="tdCustRef3">
                                    <%# Eval("CustomerReference3")%></td>
                                <td runat="server" id="tdCustRef4">
                                    <%# Eval("CustomerReference4")%></td>
                                <td>
                                    <%# Eval("RequiredShipDate", "{0:MM-dd-yyyy}")%>
                                </td>
                            </tr>
                        </ItemTemplate>
                    </asp:Repeater>
                </table>
                <div style="text-align: right;">
                    <ucCommon:PagerForGridView ID="ucPager" runat="server" OnGridDataBind="ucPager_GridDataBind" />
                </div>
            </asp:Panel>
            <div runat="server" id="divMessage" style="height: 20px; border: 1px solid; padding: 5px; text-align: center; margin-top: 20px;">
            </div>
            <div class="divMessage">
                <asp:Label runat="server" ID="lblError" CssClass="error multiLineSpan" Visible="false"></asp:Label>
                <asp:Label runat="server" ID="lblSuccess" CssClass="success multiLineSpan" Visible="false"></asp:Label>
                <cc:ConfirmDialog ID="ccConfirmDialogAccessories" runat="server" Message=""
                    OnResponse="ccConfirmDialogAccessories_Response" Visible="false" ConfirmDialogType="Custom" CssClass="borderpanel"
                    ButtonCssClass="mediumbutton" Style="width: 50%" />
            </div>
            <asp:Panel runat="server" ID="pnlEdit" Visible="true" CssClass="borderpanel marginbotton5" DefaultButton="btnNoAction">
                <h3>Kitting Details</h3>
                <asp:HiddenField runat="server" ID="hdfProjectKey" />
                <asp:HiddenField runat="server" ID="hdfOrderKey" />
                <asp:HiddenField runat="server" ID="hdfIsAcc" Value="0" />
                <asp:HiddenField runat="server" ID="hdfMaxLineToPartID" Value="0" />
                <table class="grid" cellspacing="0" rules="all" border="1" style="border-collapse: collapse;">
                    <tr>
                        <th scope="col" style="width: 9%;"></th>
                        <th scope="col" style="width: 8%;">Box ID</th>
                        <th scope="col" style="width: 7%;">Part Status </th>
                        <th scope="col" style="width: 12%;">Part Number</th>
                        <th scope="col" style="width: 12%;">Description</th>
                        <th scope="col" style="width: 5%;">QTY</th>
                        <th scope="col" style="width: 12%;">Location</th>
                        <th scope="col" style="width: 5%;">Pallet</th>
                        <th scope="col" style="width: 12%;">StockGroup</th>
                        <th scope="col" style="width: 8%;">Location for this Order</th>
                        <th scope="col" style="width: 6%;">Accessory</th>
                    </tr>
                    <asp:Repeater ID="rptOrdLineToPart" runat="server" OnItemDataBound="rptOrdLineToPart_ItemDataBound">
                        <ItemTemplate>
                            <tr class="TableRow">
                                <td>
                                    <table border="0" cellpadding="0" cellspacing="0" class="innerTable">
                                        <tr>
                                            <td>
                                                <asp:LinkButton ID="lbtnCreatePallet" runat="server" OnClick="lbtnCreatePallet_Click" CommandArgument='<%# Eval("PK_ORD_LineToPart") %>' Text='Create Pallet ID' ClientIDMode="AutoID"></asp:LinkButton>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <asp:LinkButton ID="lbtnSaveLine" runat="server" OnClick="lbtnSaveLine_Click" Text='Save'
                                                    CommandArgument='<%# Eval("PK_ORD_LineToPart") %>' ClientIDMode="AutoID"></asp:LinkButton></td>
                                </td>
                            </tr>
                            </table>                                                                      
                                <td>

                                    <asp:HiddenField runat="server" ID="hdfOrderLineToPart" Value='<%# Eval("PK_ORD_LineToPart") %>' />
                                    <asp:HiddenField runat="server" ID="hdfInvPartsKey" Value='<%# Eval("FK_INV_Parts") %>' />
                                    <asp:HiddenField runat="server" ID="hdfPalletID" Value='<%# Eval("PK_INV_Pallet") %>' />
                                    <asp:HiddenField runat="server" ID="hdfFK_MDM_Part" Value='<%# Eval("FK_MDM_Part") %>' />
                                    <%--<asp:TextBox runat="server" ID="txtBoxID" CssClass="largetextbox2"></asp:TextBox>--%>
                                    <asp:Label ID="lblBoxID" CssClass="largetextbox2" runat="server" Text='<%# Eval("BoxID") %>'></asp:Label>
                                </td>
                            <td>
                                <asp:Label ID="lblPartStatus" runat="server" Text='<%# Eval("InvPartStatus") %>'></asp:Label>
                            </td>
                            <td>
                                <%--<asp:TextBox runat="server" ID="txtPartNumber" CssClass="largetextbox2"></asp:TextBox>--%>
                                <asp:Label ID="lblPartNumber" CssClass="largetextbox2" runat="server" Text='<%# Eval("PartNumber") %>'></asp:Label>
                            </td>
                            <td>
                                <%--<asp:TextBox runat="server" ID="txtPartDesc" CssClass="largetextbox2"></asp:TextBox>--%>
                                <asp:Label ID="lblPartDesc" CssClass="largetextbox2" runat="server" Text='<%# Eval("PartDescription") %>'></asp:Label>
                            </td>
                            <td>
                                <%--<asp:TextBox runat="server" ID="txtQty" CssClass="largetextbox2"></asp:TextBox>--%>
                                <asp:Label ID="lblQty" CssClass="largetextbox2" runat="server" Text='<%# Eval("QTY") %>'></asp:Label>
                            </td>
                            <td>
                                <table border="0" cellpadding="0" cellspacing="0" class="innerTable">
                                    <tr>
                                        <td>
                                            <asp:TextBox runat="server" ID="txtLocation" CssClass="largetextbox2"></asp:TextBox>
                                        </td>
                                        <td>
                                            <asp:Label runat="server" ID="lblLocation" ForeColor="Red" Text="*"></asp:Label>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td>
                                <asp:TextBox runat="server" ID="txtPalletName" CssClass="largetextbox2"></asp:TextBox>
                                <asp:RegularExpressionValidator ID="rePalletName" runat="server" ControlToValidate="txtPalletName" ErrorMessage='<%# HP.TS.SSDO.CST.Library.Constants.ConstOnlyPositiveInteger%>' Display="Dynamic" CssClass="error" EnableClientScript="true" ValidationExpression="^\+?[1-9][0-9]*$" ValidationGroup='PalletNameValidation'></asp:RegularExpressionValidator>
                            </td>
                            <td>
                                <table border="0" cellpadding="0" cellspacing="0" class="innerTable">
                                    <tr>
                                        <td>
                                            <asp:DropDownList runat="server" ID="ddlStockGroup" Width="99%"></asp:DropDownList>
                                        </td>
                                        <td>
                                            <asp:Label runat="server" ID="lblStockGroup" ForeColor="Red" Text="*"></asp:Label>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td>                              
                                <asp:Button ID="cmdUpdate" runat="server" Text="Update" CssClass="mediumbutton" OnClientClick="SyncLocationCodeAndStockGroup(this); return false;"/>
                            </td>
                            <td>
                                <asp:RadioButton runat="server" ID="rbtnAccessory" />
                            </td>
                            </tr>
                        </ItemTemplate>
                    </asp:Repeater>
                </table>
            </asp:Panel>
            <asp:Panel runat="server" ID="pnlAddConsumablePart" Visible="true" CssClass="borderpanel marginbotton5" DefaultButton="btnNoAction">
                <h3>Add Consumable Part</h3>
                <uc:ConsumablePartControl ID="ucConsumablePartControl" runat="server" BoxStatus="Committed" OnResponseEvent="ucConsumablePartControl_ResponseEvent" />
            </asp:Panel>
            <asp:Panel runat="server" ID="pnlInvParts" Visible="true" CssClass="borderpanel marginbotton5" DefaultButton="btnNoAction">
                <h3>Inventory</h3>
                <asp:HiddenField runat="server" ID="hdfColumnSerial" />
                <asp:HiddenField runat="server" ID="hdfColumnAsset" />
                <asp:HiddenField runat="server" ID="hdfColumnMac" />
                <asp:HiddenField runat="server" ID="hdfColumnBluetoothMac" />
                <table class="grid" cellspacing="0" rules="all" border="1" style="border-collapse: collapse;">
                    <tr>
                        <th scope="col" style="width: 5%;"></th>
                        <th scope="col" style="width: 9%;">Box ID</th>
                        <th scope="col" style="width: 12%;">Part Number</th>
                        <th scope="col" style="width: 15%;">Description</th>
                        <th scope="col" style="width: 5%;">QTY</th>
                        <th scope="col" runat="server" id="thSerial">Serial</th>
                        <th scope="col" runat="server" id="thAsset">Asset</th>
                        <th scope="col" runat="server" id="thMac">Mac</th>
                         <th scope="col" runat="server" id="thBluetoothMac">Bluetooth Mac</th>
                        <th scope="col" runat="server" id="thUDF1">
                            <asp:Label runat="server" ID="lblUDF1"></asp:Label></th>
                        <th scope="col" runat="server" id="thUDF2">
                            <asp:Label runat="server" ID="lblUDF2"></asp:Label></th>
                        <th scope="col" runat="server" id="thUDF3">
                            <asp:Label runat="server" ID="lblUDF3"></asp:Label></th>
                        <th scope="col" runat="server" id="thUDF4">
                            <asp:Label runat="server" ID="lblUDF4"></asp:Label></th>
                        <th scope="col" runat="server" id="thUDF5">
                            <asp:Label runat="server" ID="lblUDF5"></asp:Label></th>
                        <th scope="col" runat="server" id="thUDF6">
                            <asp:Label runat="server" ID="lblUDF6"></asp:Label></th>
                        <th scope="col" runat="server" id="thUDF7">
                            <asp:Label runat="server" ID="lblUDF7"></asp:Label></th>
                        <th scope="col" runat="server" id="thUDF8">
                            <asp:Label runat="server" ID="lblUDF8"></asp:Label></th>
                        <th scope="col" runat="server" id="thUDF9">
                            <asp:Label runat="server" ID="lblUDF9"></asp:Label></th>
                        <th scope="col" runat="server" id="thUDF10">
                            <asp:Label runat="server" ID="lblUDF10"></asp:Label></th>
                    </tr>
                    <asp:Repeater ID="rptINVParts" runat="server" OnItemDataBound="rptINVParts_ItemDataBound">
                        <ItemTemplate>
                            <tr>
                                <td>
                                    <asp:LinkButton ID="lbtnSaveInv" runat="server" OnClick="lbtnSaveInv_Click" Text='Save' ClientIDMode="AutoID"></asp:LinkButton>
                                    <asp:HiddenField runat="server" ID="hdfPK_INV_Parts" />
                                </td>
                                <td>
                                    <%--<asp:TextBox ID="txtBoxID" runat="server" Width="90%"></asp:TextBox>--%>
                                    <asp:Label ID="lblBoxID" CssClass="largetextbox2" runat="server" Text='<%# Eval("BoxID") %>'></asp:Label>
                                </td>
                                <td>
                                    <%-- <asp:TextBox runat="server" ID="txtPartNumber" CssClass="largetextbox2"></asp:TextBox>--%>
                                    <asp:Label ID="lblPartNumber" CssClass="largetextbox2" runat="server" Text='<%# Eval("PartNumber") %>'></asp:Label>
                                </td>
                                <td>
                                    <%--<asp:TextBox runat="server" ID="txtPartDescription" CssClass="largetextbox2"></asp:TextBox>--%>
                                    <asp:Label ID="lblPartDescription" CssClass="largetextbox2" runat="server" Text='<%# Eval("PartDescription") %>'></asp:Label>
                                </td>
                                <td>
                                    <%--<asp:TextBox runat="server" ID="txtQty" CssClass="largetextbox2"></asp:TextBox>--%>
                                    <asp:Label ID="lblQty" CssClass="largetextbox2" runat="server" Text='<%# Eval("QTY") %>'></asp:Label>
                                </td>
                                <td runat="server" id="tdSerial">
                                    <asp:HiddenField runat="server" ID="hdfSerialNumberFlag" />
                                    <table border="0" cellpadding="0" cellspacing="0" class="innerTable">
                                        <tr>
                                            <td style="width: 90%;">
                                                <%--<asp:TextBox ID="txtSerialNumber" runat="server" Width="98%"></asp:TextBox>--%>
                                                <asp:Label ID="lblSerialNumber" CssClass="largetextbox2" runat="server"></asp:Label>
                                            </td>
                                            <td style="width: 10%;">
                                                <asp:Label runat="server" ID="lblSerialNumberRed" ForeColor="Red" Text="*"></asp:Label>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td runat="server" id="tdAsset">
                                    <asp:HiddenField runat="server" ID="hdfAssetFlag" />
                                    <table border="0" cellpadding="0" cellspacing="0" class="innerTable">
                                        <tr>
                                            <td style="width: 90%;">
                                                <asp:TextBox ID="txtAssetNumber" runat="server" Width="98%"></asp:TextBox>
                                            </td>
                                            <td style="width: 10%;">
                                                <asp:Label runat="server" ID="lblAssetNumberRed" ForeColor="Red" Text="*"></asp:Label>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td runat="server" id="tdMACAddress">
                                    <asp:HiddenField runat="server" ID="hdfMACAddressFlag" />
                                    <table border="0" cellpadding="0" cellspacing="0" class="innerTable">
                                        <tr>
                                            <td style="width: 90%;">
                                                <asp:TextBox ID="txtMacAddress" runat="server" Width="98%"></asp:TextBox>
                                            </td>
                                            <td style="width: 10%;">
                                                <asp:Label runat="server" ID="lblMacAddressRed" ForeColor="Red" Text="*"></asp:Label>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td runat="server" id="tdBluetoothMACAddress"> <%--Ticket 186 starts--%>
                                    <asp:HiddenField runat="server" ID="hdfBluetoothMACAddressFlag" />
                                    <table border="0" cellpadding="0" cellspacing="0" class="innerTable">
                                        <tr>
                                            <td style="width: 90%;">
                                                <asp:TextBox ID="txtBluetoothMacAddress" runat="server" Width="98%"></asp:TextBox>
                                            </td>
                                            <td style="width: 10%;">
                                                <asp:Label runat="server" ID="lblBluetoothMacAddressRed" ForeColor="Red" Text="*"></asp:Label>
                                            </td>
                                        </tr>
                                    </table>
                                </td><%--Ticket 186 ends--%>
                                <td runat="server" id="tdUDF1">
                                    <asp:HiddenField runat="server" ID="hdfUDFMandatory1" />
                                    <table border="0" cellpadding="0" cellspacing="0" class="innerTable">
                                        <tr>
                                            <td style="width: 90%;">
                                                <asp:TextBox ID="txtUDF1" runat="server" Width="98%"></asp:TextBox>
                                            </td>
                                            <td style="width: 10%;">
                                                <asp:Label runat="server" ID="lblUDF1Red" ForeColor="Red" Text="*"></asp:Label>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td runat="server" id="tdUDF2">
                                    <asp:HiddenField runat="server" ID="hdfUDFMandatory2" />
                                    <table border="0" cellpadding="0" cellspacing="0" class="innerTable">
                                        <tr>
                                            <td style="width: 90%;">
                                                <asp:TextBox ID="txtUDF2" runat="server" Width="98%"></asp:TextBox>
                                            </td>
                                            <td style="width: 10%;">
                                                <asp:Label runat="server" ID="lblUDF2Red" ForeColor="Red" Text="*"></asp:Label>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td runat="server" id="tdUDF3">
                                    <asp:HiddenField runat="server" ID="hdfUDFMandatory3" />
                                    <table border="0" cellpadding="0" cellspacing="0" class="innerTable">
                                        <tr>
                                            <td style="width: 90%;">
                                                <asp:TextBox ID="txtUDF3" runat="server" Width="98%"></asp:TextBox>
                                            </td>
                                            <td style="width: 10%;">
                                                <asp:Label runat="server" ID="lblUDF3Red" ForeColor="Red" Text="*"></asp:Label>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td runat="server" id="tdUDF4">
                                    <asp:HiddenField runat="server" ID="hdfUDFMandatory4" />
                                    <table border="0" cellpadding="0" cellspacing="0" class="innerTable">
                                        <tr>
                                            <td style="width: 90%;">
                                                <asp:TextBox ID="txtUDF4" runat="server" Width="98%"></asp:TextBox>
                                            </td>
                                            <td style="width: 10%;">
                                                <asp:Label runat="server" ID="lblUDF4Red" ForeColor="Red" Text="*"></asp:Label>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td runat="server" id="tdUDF5">
                                    <asp:HiddenField runat="server" ID="hdfUDFMandatory5" />
                                    <table border="0" cellpadding="0" cellspacing="0" class="innerTable">
                                        <tr>
                                            <td style="width: 90%;">
                                                <asp:TextBox ID="txtUDF5" runat="server" Width="98%"></asp:TextBox>
                                            </td>
                                            <td style="width: 10%;">
                                                <asp:Label runat="server" ID="lblUDF5Red" ForeColor="Red" Text="*"></asp:Label>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td runat="server" id="tdUDF6">
                                    <asp:HiddenField runat="server" ID="hdfUDFMandatory6" />
                                    <table border="0" cellpadding="0" cellspacing="0" class="innerTable">
                                        <tr>
                                            <td style="width: 90%;">
                                                <asp:TextBox ID="txtUDF6" runat="server" Width="98%"></asp:TextBox>
                                            </td>
                                            <td style="width: 10%;">
                                                <asp:Label runat="server" ID="lblUDF6Red" ForeColor="Red" Text="*"></asp:Label>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td runat="server" id="tdUDF7">
                                    <asp:HiddenField runat="server" ID="hdfUDFMandatory7" />
                                    <table border="0" cellpadding="0" cellspacing="0" class="innerTable">
                                        <tr>
                                            <td style="width: 90%;">
                                                <asp:TextBox ID="txtUDF7" runat="server" Width="98%"></asp:TextBox>
                                            </td>
                                            <td style="width: 10%;">
                                                <asp:Label runat="server" ID="lblUDF7Red" ForeColor="Red" Text="*"></asp:Label>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td runat="server" id="tdUDF8">
                                    <asp:HiddenField runat="server" ID="hdfUDFMandatory8" />
                                    <table border="0" cellpadding="0" cellspacing="0" class="innerTable">
                                        <tr>
                                            <td style="width: 90%;">
                                                <asp:TextBox ID="txtUDF8" runat="server" Width="98%"></asp:TextBox>
                                            </td>
                                            <td style="width: 10%;">
                                                <asp:Label runat="server" ID="lblUDF8Red" ForeColor="Red" Text="*"></asp:Label>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td runat="server" id="tdUDF9">
                                    <asp:HiddenField runat="server" ID="hdfUDFMandatory9" />
                                    <table border="0" cellpadding="0" cellspacing="0" class="innerTable">
                                        <tr>
                                            <td style="width: 90%;">
                                                <asp:TextBox ID="txtUDF9" runat="server" Width="98%"></asp:TextBox>
                                            </td>
                                            <td style="width: 10%;">
                                                <asp:Label runat="server" ID="lblUDF9Red" ForeColor="Red" Text="*"></asp:Label>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td runat="server" id="tdUDF10">
                                    <asp:HiddenField runat="server" ID="hdfUDFMandatory10" />
                                    <table border="0" cellpadding="0" cellspacing="0" class="innerTable">
                                        <tr>
                                            <td style="width: 90%;">
                                                <asp:TextBox ID="txtUDF10" runat="server" Width="98%"></asp:TextBox>
                                            </td>
                                            <td style="width: 10%;">
                                                <asp:Label runat="server" ID="lblUDF10Red" ForeColor="Red" Text="*"></asp:Label>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </ItemTemplate>
                    </asp:Repeater>
                </table>
                </div>
            </asp:Panel>
            <div style="float: left">
                <asp:UpdateProgress ID="UpdateProgress1" ClientIDMode="Static" runat="server" AssociatedUpdatePanelID="uptPnl">
                    <ProgressTemplate>
                        <img src="/Images/loading.gif" />
                    </ProgressTemplate>
                </asp:UpdateProgress>
            </div>
            <div class="btnDiv">
                <asp:Button runat="server" ID="cmdBackToList" CssClass="mediumbutton" Text="Back" CausesValidation="false" Visible="false" OnClientClick="window.location=window.location; return false;" />&nbsp;
                <asp:Button ID="btnSendtoShipping" runat="server" CssClass="mediumbutton btnposition" Text="Send to Shipping" OnClick="btnSendtoShipping_Click" />
                <asp:Button ID="btnReturntoStock" runat="server" CssClass="mediumbutton btnposition" Text="Return to Stock" OnClick="btnReturntoStock_Click" />
                &nbsp;&nbsp;&nbsp;
                   <asp:Label runat="server" ID="lblTargetProject" CssClass="largetextbox" Text="Target Project:"></asp:Label>
                &nbsp;&nbsp;
                <asp:DropDownList runat="server" ID="ddlTargetProject" CssClass="largetextbox" Width="200px" AppendDataBoundItems="false">
                </asp:DropDownList>
                &nbsp;
                <asp:Button ID="btnTransfer" runat="server" CssClass="mediumbutton btnposition" Text="Transfer Order" OnClick="btnTransfer_Click" />

            </div>

            <div>
                <asp:Button runat="server" ID="btnNoAction" CssClass="mediumbutton" Text="NoAction" Enabled="false" Style="display: none" />
            </div>
            <script type="text/javascript">
                function pageLoad() {
                    $('input.valid-number').bind('keypress', function (e) {
                        return (e.which != 13 && e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) ? false : true;
                    })
                }
            </script>
            <script type="text/javascript">
                function pageLoad() {
                    $('input.valid-number').bind('keypress', function (e) {
                        return (e.which != 13 && e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) ? false : true;
                    })
                    var msg = $('.divMessage');
                    for (i = 0; i < msg.length; i++) {
                        if ($(msg[i]).html().length > 10) {
                            $(window).scrollTop($(msg[i]).offset().top - 40);
                        }
                    }
                }

                function SyncLocationCodeAndStockGroup(obj) {
                    SyncLocationCode(obj);
                    SyncStockGroup(obj);
                    SyncPallet(obj);
                    return false;
                }

                function SyncLocationCode(obj) {
                    var locationCode = $("#ContentPlaceHolder1_rptOrdLineToPart_txtLocation_0").val();
                    $("input[id^=ContentPlaceHolder1_rptOrdLineToPart_txtLocation_]").each(function () {
                        $(this).val(locationCode);
                    });
                }

                function SyncStockGroup(obj) {
                    var sg = $('#ContentPlaceHolder1_rptOrdLineToPart_ddlStockGroup_0').val();
                    if (sg.length > 0) {
                        $("select[id^=ContentPlaceHolder1_rptOrdLineToPart_ddlStockGroup_]").each(function () {
                            $(this).val(sg);
                        });
                    }
                }

                function SyncPallet(obj)
                {
                    var palletid = $("#ContentPlaceHolder1_rptOrdLineToPart_txtPalletName_0").val();
                    $("input[id^=ContentPlaceHolder1_rptOrdLineToPart_txtPalletName_]").each(function () {
                        $(this).val(palletid);
                    });
                }
            </script>
        </ContentTemplate>
        <Triggers>
        </Triggers>
    </asp:UpdatePanel>
</asp:Content>


