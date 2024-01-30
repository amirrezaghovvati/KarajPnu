package com.example.daneshgahpayamnoor.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daneshgahpayamnoor.R;
import com.example.daneshgahpayamnoor.Result.Student;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List<Student> students=new ArrayList<>();
    private StudentEvent event;
    private PersianDate persianDate;
    private PersianDateFormat persianDateFormat;

    public StudentAdapter(StudentEvent studentEvent){
        this.event=studentEvent;
        persianDate=new PersianDate();
        persianDateFormat=new PersianDateFormat("Y/m/d");


    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list,parent,false);
        return new StudentViewHolder(view);
    }
    public void setData(List<Student> students){
        this.students=students;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.bindStudent(students.get(position));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder{
        private TextView tvExamName;
        private TextView tvExamType;
        private TextView tvExamId;
        private TextView tvBuilding;
        private TextView tvSeatNo;
        private TextView tvHour;
        private TextView tvDate;
        private TextView tvRow;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExamName=itemView.findViewById(R.id.tvExamName);
            tvExamType=itemView.findViewById(R.id.tvExamType);
            tvExamId=itemView.findViewById(R.id.tvCourseId);
            tvBuilding=itemView.findViewById(R.id.tvBuilding);
            tvSeatNo=itemView.findViewById(R.id.tvSeatNo);
            tvHour=itemView.findViewById(R.id.tvHour);
            tvDate=itemView.findViewById(R.id.tvDate);
            tvRow=itemView.findViewById(R.id.tvRow);

        }
        public void bindStudent(Student student){
            tvExamName.setText(student.getCourse_name());
            tvExamType.setText(student.getExam_type());
            tvExamId.setText("کد درس ( "+student.getCourse_id()+" )");
            tvBuilding.setText(student.getBuilding());
            event.onResult(" "+student.getLast_name(),student.getStudent_id(),student.getDegree());
            tvSeatNo.setText(student.getSeat_no());


            String[] strings= student.getExam_date().split("-");
            persianDate.setGrgDay(Integer.parseInt(strings[1]));
            String[] stringsHour=student.getExam_time().split(":");
            persianDate.setHour(Integer.parseInt(stringsHour[0]));
            persianDate.setMinute(Integer.parseInt(stringsHour[1]));
            if (persianDate.getMinute()==00){
                tvHour.setText("ساعت :  "+persianDate.getHour()+":"+persianDate.getMinute()+"0");
            }else {
                tvHour.setText("ساعت :  "+persianDate.getHour()+":"+persianDate.getMinute());
            }

            persianDate.initGrgDate(Integer.parseInt(strings[0]),Integer.parseInt(strings[1]),Integer.parseInt(strings[2]));
            persianDateFormat.format(persianDate);

            tvDate.setText("تاریخ :  "+String.valueOf(persianDateFormat.format(persianDate)));
            tvRow.setText( student.getRow());

        }
    }
    public interface StudentEvent{
        void onResult(String name,String studentID,String semester);
    }
}
