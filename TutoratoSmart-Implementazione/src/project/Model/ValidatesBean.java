package project.Model;

import java.io.Serializable;

/**
 * Questa classe rappresenta l'associazione Valida tra l'entita' Attivita' e l'entita' Utente.
 */
public class ValidatesBean implements Serializable {
	private static final long serialVersionUID = -8762723026814198860L;
	
	private String commissionMember;
	private int activityId;
	
	public ValidatesBean() {
		commissionMember = "";
		activityId = -1;
	}
	
	
	/**
	 * @param commissionMember membro della commissione
	 * @param activityId identificativo dell'attivita'
	 */
	public ValidatesBean(String commissionMember, int activityId) {
		this.commissionMember=commissionMember;
		this.activityId=activityId;
	}

	
	/**
	 * @return commissionMember membro della commissione
	 */
	public String getCommissionMember() {
		return commissionMember;
	}

	/**
	 * @param commissionMember commissionMember membro della commissione da assegnare all'associazione Valida
	 */
	public void setCommissionMember(String commissionMember) {
		this.commissionMember = commissionMember;
	}

	/**
	 * @return activityId identificativo dell'attivita'
	 */
	public int getActivityId() {
		return activityId;
	}

	/**
	 * @param activityId activityId identificativo dell'attivita' da assegnare all'associazione Valida
	 */
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
}