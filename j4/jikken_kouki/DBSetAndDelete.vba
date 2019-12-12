Const orderSET As Integer = 0
Const orderDELETE As Integer = 1

Private Sub DESet_Click()
    DBoperation (0)
    MsgBox "格納完了"
End Sub

Private Sub DBDelete_Click()
    DBoperation (1)
    MsgBox "削除完了"
End Sub

Sub DBoperation(order As Integer)
    Dim connect     As Object
    Dim userID      As String
    Dim password    As String
    Dim surverName  As String
    Dim DBName      As String
    Dim monitorTime As Long
    Dim mode        As Long
    Dim recordSet As Object
    Dim cursorEngine As Long
    Dim cursorType As Long
    Dim lockType As Long
    Dim opt As Long
    Dim source As String
    Const code      As String = "code"
    Const name      As String = "name"
    Const address   As String = "address"
    Const phone     As String = "phone"
    Const labors    As String = "labors"
    
    Set connect = New ADODB.Connection
    userID = "td16131"
    password = "td16131"
    surverName = "jocalc1"
    DBName = "r01_j4_exp"
    monitorTime = 10
    connect.ConnectionString = "driver={PostgreSQL Unicode(x64)}" & _
                               ";uid=" & userID & _
                               ";pwd=" & password & _
                               ";server=" & surverName & _
                               ";database=" & DBName
    connect.ConnectionTimeOut = monitorTime
    mode = adModeReadWrite
    connect.mode = mode
    connect.Open
    
    Set recordSet = New ADODB.recordSet
    cursorEngine = adUseClient
    recordSet.CursorLocation = cursorEngine
    cursorType = adOpenDynamic
    lockType = adLockOptimistic
    opt = adCmdText
    source = "select * from company_21"
    recordSet.Open source, connect, cursorType, lockType, opt

    Select Case order
        Case orderSET
            With ActiveSheet
                On Error Resume Next
                For i = 2 To .Cells(Rows.Count, 1).End(xlUp).Row
                    recordSet.AddNew
                        recordSet.Fields(code).Value = .Cells(i, 1).Value
                        recordSet.Fields(name).Value = .Cells(i, 2).Value
                        recordSet.Fields(address).Value = .Cells(i, 3).Value
                        recordSet.Fields(phone).Value = .Cells(i, 4).Value
                        recordSet.Fields(labors).Value = .Cells(i, 5).Value
                     recordSet.Update
                Next
                recordSet.Close
            End With
            
        Case orderDELETE
            Do Until recordSet.EOF
                recordSet.Delete
                recordSet.MoveNext
            Loop
            
        Case Else
            MsgBox "不正なオーダーです"
    End Select
    
    connect.Close
End Sub