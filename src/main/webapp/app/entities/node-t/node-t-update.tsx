import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDetailT } from 'app/shared/model/detail-t.model';
import { getEntities as getDetailTs } from 'app/entities/detail-t/detail-t.reducer';
import { IModuleT } from 'app/shared/model/module-t.model';
import { getEntities as getModuleTs } from 'app/entities/module-t/module-t.reducer';
import { getEntity, updateEntity, createEntity, reset } from './node-t.reducer';
import { INodeT } from 'app/shared/model/node-t.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INodeTUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface INodeTUpdateState {
  isNew: boolean;
  idsdetailT: any[];
  moduleTId: string;
}

export class NodeTUpdate extends React.Component<INodeTUpdateProps, INodeTUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsdetailT: [],
      moduleTId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getDetailTs();
    this.props.getModuleTs();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { nodeTEntity } = this.props;
      const entity = {
        ...nodeTEntity,
        ...values,
        detailTS: mapIdList(values.detailTS)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/node-t');
  };

  render() {
    const { nodeTEntity, detailTS, moduleTS, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="monoHeartApp.nodeT.home.createOrEditLabel">Create or edit a NodeT</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : nodeTEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="node-t-id">ID</Label>
                    <AvInput id="node-t-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="node-t-name">
                    Name
                  </Label>
                  <AvField id="node-t-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label for="node-t-detailT">Detail T</Label>
                  <AvInput
                    id="node-t-detailT"
                    type="select"
                    multiple
                    className="form-control"
                    name="detailTS"
                    value={nodeTEntity.detailTS && nodeTEntity.detailTS.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {detailTS
                      ? detailTS.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/node-t" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  detailTS: storeState.detailT.entities,
  moduleTS: storeState.moduleT.entities,
  nodeTEntity: storeState.nodeT.entity,
  loading: storeState.nodeT.loading,
  updating: storeState.nodeT.updating,
  updateSuccess: storeState.nodeT.updateSuccess
});

const mapDispatchToProps = {
  getDetailTs,
  getModuleTs,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NodeTUpdate);
