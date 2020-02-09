import com.google.gson.Gson;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.util.Vector;

public class TableContent extends AbstractTableModel {

    Vector rowData, columnNames;

    public TableContent() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .addHeader("Authorization", Credentials.basic("zephyrfloraf@gmail.com","1234abcd"))
                .url("https://api-lab-trone-stif.opendata.stif.info/service/tr-vianavigo/departures?line_id=810%3AB&stop_point_id=8775864%3A810%3AB")
                .build();

        columnNames = new Vector();
        columnNames.add("Vehicle");
        columnNames.add("Direction");
        columnNames.add("Schedule");

        rowData = new Vector();

        try (Response response = client.newCall(request).execute()) {
            Passage[] passages = new Gson().fromJson(response.body().string(), Passage[].class);
            for (int i = 0; i < passages.length; i++){
                Vector tempRow = new Vector();
                tempRow.add(passages[i].getVehicleName());
                tempRow.add(passages[i].getLineDirection());
                if (passages[i].getTime() == 0){
                    tempRow.add(passages[i].getSchedule());
                } else if(passages[i].getTime() == 1){
                    tempRow.add(passages[i].getTime()+" minute");
                } else {
                    tempRow.add(passages[i].getTime() + " minutes");
                }
                rowData.add(tempRow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getRowCount() {
        return this.rowData.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((Vector)(this.rowData.get(rowIndex))).get(columnIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return (String)this.columnNames.get(columnIndex);
    }
}
