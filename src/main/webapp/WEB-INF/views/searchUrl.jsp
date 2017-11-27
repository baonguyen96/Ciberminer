<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.16/css/jquery.dataTables.css">

    <script type="text/javascript" charset="utf8" src="//cdn.datatables.net/1.10.16/js/jquery.dataTables.js"></script>

    <script src="resources/js/jquery-paginate.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        .hea{
            font-family:Helvetica Neue,Helvetica,Arial,sans-serif;
            font-size: 14px;
            line-height: 20px;
            text-align: center;
            color: #fff;
            padding-top: 15px;
            padding-bottom: 15px;
            background-color: #222;
            border-color: #080808;
        }
        .page-navigation a {
            margin: 0 2px;
            display: inline-block;
            padding: 3px 5px;
            color: #ffffff;
            background-color: #70b7ec;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
        }

        .page-navigation a[data-selected] {
            background-color: #3d9be0;
        }
    </style>
<script>
        $(document).ready(function () {
            $('#searchResults').DataTable();
        })

        function updateHitrate(e,id,location){
            e.preventDefault();
            ajaxfunction(id);
            window.open(location,'_blank');
        }
        function ajaxfunction(pId){

            var submit_url = "/url/hitrate?urlId="+pId;
            $.ajax({
                type : "POST",
                contentType : "application/json",
                url : submit_url,
                success : function(data) {
                    console.log("SUCCESS: ", data);
                },
                error : function(e) {
                    console.log("ERROR: ", e);
                },
                done : function(e) {
                    console.log("DONE");
                }
            });

        }
    </script>

</head>
<body>

<div class="navbar navbar-inverse navbar-fixed-top hea">Your search returned the following results!</div>

<div align="center" style="padding-top: 70px;">
    <a href="home" class="btn btn-primary" role="button" >Go back to Search</a>
</div>

<div style ="padding-top: 70px;padding: 1.5rem;margin-right: 0; margin-bottom: 0; margin-left: 0; border-width: .2rem;">
    <table id="searchResults" class="table table-hover table-bordered">
        <thead class="thead-inverse">
        <tr>
            <th>URL</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${res.getUrlList()}" var="temp">
            <tr>
                <td>
                    <a href="${temp.getUrlLink()}" onclick="updateHitrate(event,${temp.getId()},'${temp.getUrlLink()}')" target="_blank" id="url">${temp.getUrlLink()}</a>
                </td>
                <td>
                    <div id="description">${temp.getDescription()}</div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>